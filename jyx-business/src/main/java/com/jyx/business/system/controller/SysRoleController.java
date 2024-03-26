package com.jyx.business.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyx.authentication.domain.LoginUser;
import com.jyx.authentication.service.TokenService;
import com.jyx.business.system.entity.BaseController;
import com.jyx.business.system.entity.TableDataInfo;
import com.jyx.core.base.domain.R;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.domain.Role;
import com.jyx.system.domain.UserRole;
import com.jyx.system.param.QueryRoleParam;
import com.jyx.system.param.RoleParam;
import com.jyx.system.param.UserParam;
import com.jyx.system.service.RoleService;
import com.jyx.system.service.UserService;
import com.jyx.system.serviceImpl.SysPermissionService;
import com.jyx.system.vo.RoleVo;
import com.jyx.system.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * 角色信息
 *
 * @author ruoyi
 */
@Tag(name = "角色信息接口")
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    @Resource
    private RoleService roleService;

    @Resource
    private TokenService tokenService;

    @Resource
    private SysPermissionService permissionService;

    @Resource
    private UserService userService;


    @Operation(summary = "角色分页查询")
    @GetMapping("/list")
    public TableDataInfo list(QueryRoleParam role) {
        IPage<Role> list = roleService.selectRolePage(role);
        return getDataTable(list);
    }

    /**
     * 根据角色编号获取详细信息
     */
    @Operation(summary = "根据角色编号获取详细信息")
    @GetMapping(value = "/{roleId}")
    public R<Role> getInfo(@PathVariable Long roleId) {
        return R.ok(roleService.selectRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @Operation(summary = "新增角色")
    @PostMapping
    public R add(@Valid @RequestBody RoleParam role) {
        if (!roleService.checkRoleNameUnique(role)) {
            return R.fail("新增角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return R.fail("新增角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setCreateBy(getUsername());
        return R.ok(roleService.insertRole(role));

    }

    /**
     * 修改保存角色
     */
    @Operation(summary = "修改保存角色")
    @PutMapping
    public R edit(@Valid @RequestBody RoleParam role) {
        roleService.checkRoleAllowed(role.getRoleId());
        if (!roleService.checkRoleNameUnique(role)) {
            return R.fail("修改角色'" + role.getRoleName() + "'失败，角色名称已存在");
        } else if (!roleService.checkRoleKeyUnique(role)) {
            return R.fail("修改角色'" + role.getRoleName() + "'失败，角色权限已存在");
        }
        role.setUpdateBy(getUsername());

        if (roleService.updateRole(role)) {
            // 更新缓存用户权限
            LoginUser loginUser = getLoginUser();
            if (StringUtils.isNotNull(loginUser.getUser()) && userService.isAdmin(loginUser.getUserId())) {
                loginUser.setPermissions(permissionService.getMenuPermission(loginUser.getUser()));
                loginUser.setUser(userService.selectUserByUserName(loginUser.getUser().getUserName()));
                tokenService.setLoginUser(loginUser);
            }
            return R.ok();
        }
        return R.fail("修改角色'" + role.getRoleName() + "'失败，请联系管理员");
    }

    /**
     * 状态修改
     */
    @Operation(summary = "状态修改")
    @PutMapping("/changeStatus")
    public R changeStatus(@RequestBody Role role) {
        roleService.checkRoleAllowed(role.getRoleId());
        role.setUpdateBy(getUsername());
        return R.ok(roleService.updateRoleStatus(role));
    }

    /**
     * 删除角色
     */
    @Operation(summary = "删除角色")
    @DeleteMapping("/{roleIds}")
    public R remove(@PathVariable Long[] roleIds) {
        return R.ok(roleService.deleteRoleByIds(roleIds));
    }

    /**
     * 获取角色选择框列表
     */
    @Operation(summary = "获取角色选择框列表")
    @GetMapping("/optionselect")
    public R<List<RoleVo>> optionselect() {
        return R.ok(roleService.selectRoleAll());
    }

    /**
     * 查询已分配用户角色列表
     */
    @Operation(summary = "查询已分配用户角色列表")
    @GetMapping("/authUser/allocatedList")
    public TableDataInfo<UserVo> allocatedList(UserParam user) {
        IPage<UserVo> list = userService.selectAllocatedList(user);
        return getDataTable(list);
    }

    /**
     * 查询未分配用户角色列表
     */
    @Operation(summary = "查询未分配用户角色列表")
    @GetMapping("/authUser/unallocatedList")
    public TableDataInfo<UserVo> unallocatedList(UserParam user) {
        IPage<UserVo> list = userService.selectUnallocatedList(user);
        return getDataTable(list);
    }

    /**
     * 取消授权用户
     */
    @Operation(summary = "取消授权用户")
    @PutMapping("/authUser/cancel")
    public R<Boolean> cancelAuthUser(@RequestBody UserRole userRole) {
        return R.ok(roleService.deleteAuthUser(userRole));
    }

    /**
     * 批量取消授权用户
     */
    @Operation(summary = "批量取消授权用户")
    @PutMapping("/authUser/cancelAll")
    public R<Boolean> cancelAuthUserAll(Long roleId, Long[] userIds) {
        return R.ok(roleService.deleteAuthUsers(roleId, userIds));
    }

    /**
     * 批量选择用户授权
     */
    @Operation(summary = "批量选择用户授权")
    @PutMapping("/authUser/selectAll")
    public R<Boolean> selectAuthUserAll(Long roleId, Long[] userIds) {
        return R.ok(roleService.insertAuthUsers(roleId, userIds));
    }
}
