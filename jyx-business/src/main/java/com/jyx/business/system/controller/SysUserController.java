package com.jyx.business.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyx.authentication.utils.SecurityUtils;
import com.jyx.business.system.entity.AjaxResult;
import com.jyx.business.system.entity.BaseController;
import com.jyx.business.system.entity.TableDataInfo;
import com.jyx.core.base.domain.R;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.domain.User;
import com.jyx.system.param.UserParam;
import com.jyx.system.service.RoleService;
import com.jyx.system.service.UserService;
import com.jyx.system.vo.RoleVo;
import com.jyx.system.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@Tag(name = "用户信息接口")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    /**
     * 获取用户列表
     */
    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public TableDataInfo list(UserParam user) {
        IPage<User> page = userService.selectUserPage(user);
        return getDataTable(page);
    }


    /**
     * 根据用户编号获取详细信息
     */
    @Operation(summary = "根据用户编号获取详细信息")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) {
        List<RoleVo> roles = roleService.selectRoleAll();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("roles", userService.isAdmin(userId) ? roles : roles.stream().filter(r -> !roleService.isAdmin(r.getRoleId())).collect(Collectors.toList()));
        if (StringUtils.isNotNull(userId)) {
            User sysUser = userService.selectUserById(userId);
            List<Long> roleIds = roleService.selectRoleListByUserId(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("roleIds", roleIds);
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @Operation(summary = "新增用户")
    @PostMapping
    public R add(@Valid @RequestBody UserVo user) {
        String usernameMsg = userService.usernameCheck(user.getUserName());
        if (StringUtils.isNotEmpty(usernameMsg)) {
            return R.fail(usernameMsg);
        }
        String passwordMsg = userService.passwordCheck(user.getPassword());
        if (StringUtils.isNotEmpty(passwordMsg)) {
            return R.fail(passwordMsg);
        }
        if (!userService.checkUserNameUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        return R.ok(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @Operation(summary = "修改用户")
    @PutMapping
    public R edit(@Valid @RequestBody UserVo user) {
        String usernameMsg = userService.usernameCheck(user.getUserName());
        if (StringUtils.isNotEmpty(usernameMsg)) {
            return R.fail(usernameMsg);
        }
        userService.checkUserAllowed(user.getUserId());
        if (!userService.checkUserNameUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user)) {
            return R.fail("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(getUsername());
        return R.ok(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @Operation(summary = "删除用户")
    @DeleteMapping("/{userIds}")
    public R remove(@PathVariable Long[] userIds) {
        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.fail("当前用户不能删除");
        }
        return R.ok(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @Operation(summary = "重置密码")
    @PutMapping("/resetPwd")
    public R resetPwd(@RequestBody User user) {
        userService.checkUserAllowed(user.getUserId());
        String password = user.getPassword();
        String msg = userService.passwordCheck(password);
        if (!StringUtils.isEmpty(msg)) {
            return R.fail(msg);
        }
        user.setPassword(SecurityUtils.encryptPassword(password));
        user.setUpdateBy(getUsername());
        return R.ok(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @Operation(summary = "状态修改")
    @PutMapping("/changeStatus")
    public R<Integer> changeStatus(@RequestBody User user) {
        userService.checkUserAllowed(user.getUserId());
        user.setUpdateBy(getUsername());
        return R.ok(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @Operation(summary = "根据用户编号获取授权角色")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId) {
        AjaxResult ajax = AjaxResult.success();
        User user = userService.selectUserById(userId);
        List<RoleVo> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", userService.isAdmin(userId) ? roles : roles.stream().filter(r -> !roleService.isAdmin(r.getRoleId())).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @Operation(summary = "用户授权角色")
    @PutMapping("/authRole")
    public R insertAuthRole(Long userId, Long[] roleIds) {
        userService.insertUserAuth(userId, roleIds);
        return R.ok();
    }
}
