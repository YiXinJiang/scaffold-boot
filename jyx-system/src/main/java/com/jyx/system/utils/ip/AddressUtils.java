package com.jyx.system.utils.ip;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyx.core.utils.StringUtils;
import com.jyx.httpclient.RestTemplateService;
import com.jyx.system.utils.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

/**
 * @ClassName: AddressUtils
 * @Description: IP获取地址
 * @Author: tgb
 * @Date: 2024-03-12 13:48
 * @Version: 1.0
 **/
public class AddressUtils {
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    // IP地址查询
    private static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    private static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip) {
        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        String addressEnabled = SpringUtils.getRequiredProperty("jyx.addressEnabled");
        if (Boolean.parseBoolean(addressEnabled)) {
            try {
                RestTemplateService restTemplateService = SpringUtils.getBean(RestTemplateService.class);
                ResponseEntity<String> entity = restTemplateService.get(IP_URL + "?" + "ip=" + ip + "&json=true", String.class);
                String rspStr = entity.getBody();
                if (StringUtils.isEmpty(rspStr)) {
                    log.error("获取地理位置异常 {}", ip);
                    return UNKNOWN;
                }
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(rspStr);
                String region = jsonNode.get("pro").asText();
                String city = jsonNode.get("city").asText();
                return String.format("%s %s", region, city);
            } catch (Exception e) {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return UNKNOWN;
    }
}
