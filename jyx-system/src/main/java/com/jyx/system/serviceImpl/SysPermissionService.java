package com.jyx.system.serviceImpl;

import com.jyx.system.domain.User;
import com.jyx.system.service.MenuService;
import com.jyx.system.service.RoleService;
import com.jyx.system.service.UserService;
import com.jyx.system.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName: SysPermissionService
 * @Description: 用户权限处理
 * @Author: tgb
 * @Date: 2024-03-06 11:07
 * @Version: 1.0
 **/
@Component
public class SysPermissionService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    /**
     * 获取角色数据权限
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(User user) {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (userService.isAdmin(user.getUserId())) {
            roles.add("admin");
        } else {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     *
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(User user) {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (userService.isAdmin(user.getUserId())) {
            perms.add("*:*:*");
        } else {
            List<RoleVo> list = roleService.selectRoleAll();
            if (!CollectionUtils.isEmpty(list)) {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (RoleVo role : list) {
                    Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                    role.setPermissions(rolePerms);
                    perms.addAll(rolePerms);
                }
            } else {
                perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }
}
