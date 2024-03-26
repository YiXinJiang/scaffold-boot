package com.jyx.core.desensitize.logger;

import com.jyx.core.desensitize.DesensitizeConfiguration;
import com.jyx.core.desensitize.SensitivityEnum;
import com.jyx.core.utils.StringUtils;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: MaskingAppender
 * @Description:
 * @Author: jyx
 * @Date: 2024-03-15 10:22
 **/
@Plugin(name = "CustomPatternLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class CustomPatternLayout extends AbstractStringLayout {

    private static final Logger logger = LoggerFactory.getLogger(CustomPatternLayout.class);

    private final PatternLayout patternLayout;

    protected CustomPatternLayout(Charset charset, String pattern) {
        super(charset);
        patternLayout = PatternLayout.newBuilder().withPattern(pattern).build();
    }

    @PluginFactory
    public static Layout createLayout(@PluginAttribute(value = "pattern") final String pattern,
                                      @PluginAttribute(value = "charset") final Charset charset) {
        return new CustomPatternLayout(charset, pattern);
    }

    @Override
    public String toSerializable(LogEvent event) {
        return DesensitizeConfiguration.logIsEnable() ?
                hideMarkLog(patternLayout.toSerializable(event)) : patternLayout.toSerializable(event);
    }

    /**
     * 处理日志信息，进行脱敏
     * 1.判断配置文件中是否已经配置需要脱敏字段
     * 2.判断内容是否有需要脱敏的敏感信息
     * 2.1 没有需要脱敏信息直接返回
     * 2.2 处理: 身份证 ,姓名,手机号,地址敏感信息
     */
    public String hideMarkLog(final String logStr) {
        try {
            for (Map.Entry<String, SensitivityEnum> entry : DesensitizeConfiguration.regularMap.entrySet()) {
                if (logStr.contains(entry.getKey())) {
                    return matchingAndEncrypt(logStr, entry.getKey(), entry.getValue());
                }
            }
            return logStr;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return logStr;
        }
    }

    /**
     * 正则匹配对应的对象。
     *
     * @param msg     日志对象
     * @param keyType key类型
     * @param key     字段
     * @return 找到对应对象
     */
    private static String matchingAndEncrypt(String msg, String key, SensitivityEnum keyType) {
        Pattern pattern = Pattern.compile(keyType.logFinder());
        Matcher matcher = pattern.matcher(msg);
        int length = key.length() + 5;
        String hiddenStr;
        StringBuilder result = new StringBuilder(msg);
        int i = 0;
        while (matcher.find()) {
            String originStr = matcher.group();
            // 计算关键词和需要脱敏词的距离小于5
            i = msg.indexOf(originStr, i);
            if (i < 0) {
                continue;
            }
            int span = i - length;
            int startIndex = Math.max(span, 0);
            String substring = msg.substring(startIndex, i);
            if (StringUtils.isBlank(substring) || !substring.contains(key)) {
                i += key.length();
                continue;
            }
            hiddenStr = keyType.desensitizer().apply(originStr);
            msg = result.replace(i, i + originStr.length(), hiddenStr).toString();
        }
        return msg;
    }

}
