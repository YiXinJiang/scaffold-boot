package com.jyx.business.system.controller;

import com.jyx.business.system.entity.BaseController;
import com.jyx.business.system.entity.RegisterBody;
import com.jyx.business.system.service.SysRegisterService;
import com.jyx.core.base.domain.R;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.constants.ConfigConstants;
import com.jyx.system.service.ConfigService;
import com.jyx.system.utils.PasswordUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 注册验证
 *
 * @author ruoyi
 */
@Tag(name = "注册验证接口")
@RestController
public class SysRegisterController extends BaseController {
    @Autowired
    private SysRegisterService registerService;

    @Autowired
    private ConfigService configService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterBody user) {
        PasswordUtil.passwordDecrypt(user);
        if (!("true".equals(configService.selectConfigByKey(ConfigConstants.REGISTRY_CONFIG_KEY)))) {
            return R.fail("当前系统没有开启注册功能！");
        }
        String msg = registerService.register(user);
        return StringUtils.isEmpty(msg) ? R.ok() : R.fail(msg);
    }
}
