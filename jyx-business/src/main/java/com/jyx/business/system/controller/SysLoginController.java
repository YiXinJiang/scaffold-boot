package com.jyx.business.system.controller;

import com.jyx.authentication.utils.SecurityUtils;
import com.jyx.business.system.entity.AjaxResult;
import com.jyx.business.system.service.SysLoginService;
import com.jyx.core.base.domain.model.LoginBody;
import com.jyx.core.constants.Constants;
import com.jyx.system.domain.User;
import com.jyx.system.service.MenuService;
import com.jyx.system.serviceImpl.SysPermissionService;
import com.jyx.system.utils.PasswordUtil;
import com.jyx.system.vo.MenuVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 */
@Tag(name = "登录验证接口")
@RestController
public class SysLoginController {
    @Resource
    private SysLoginService loginService;

    @Resource
    private MenuService menuService;

    @Resource
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @Operation(summary = "登录方法")
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        PasswordUtil.passwordDecrypt(loginBody);
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @Operation(summary = "获取用户信息")
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        User user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @Operation(summary = "获取路由信息")
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<MenuVo> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
