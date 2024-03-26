package com.jyx.system.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.core.constants.UserConstants;
import com.jyx.core.exception.ServiceException;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.domain.Role;
import com.jyx.system.domain.RoleMenu;
import com.jyx.system.domain.UserRole;
import com.jyx.system.mapper.RoleMapper;
import com.jyx.system.param.QueryRoleParam;
import com.jyx.system.param.RoleParam;
import com.jyx.system.service.RoleMenuService;
import com.jyx.system.service.RoleService;
import com.jyx.system.service.UserRoleService;
import com.jyx.system.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author tgb
 * @since 2024-03-04 06:04:34
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 根据条件查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<Role> selectRoleList(QueryRoleParam role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public IPage<Role> selectRolePage(QueryRoleParam role) {
        Page<Role> page = new Page<>(role.getPageNum(), role.getPageSize());
        page.setOptimizeCountSql(false);
        return roleMapper.selectRolePage(page, role);
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<RoleVo> selectRolesByUserId(Long userId) {
        List<Role> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<RoleVo> List = selectRoleAll();
        for (RoleVo role : List) {
            for (Role userRole : userRoles) {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue()) {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return List;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        List<Role> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<RoleVo> selectRoleAll() {
        List<Role> roleList = list();
        List<RoleVo> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(roleList)) {
            return list;
        }
        for (Role role : roleList) {
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role, roleVo);
            if (!role.getStatus().equals(UserConstants.ROLE_DISABLE)) {
                roleVo.setFlag(true);
            }
            list.add(roleVo);
        }
        return list;
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public Role selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleNameUnique(RoleParam role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleNameUnique(role.getRoleName());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean checkRoleKeyUnique(RoleParam role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        Role info = roleMapper.checkRoleKeyUnique(role.getRoleKey());
        if (StringUtils.isNotNull(info) && info.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param roleId 角色id
     */
    @Override
    public void checkRoleAllowed(Long roleId) {
        if (StringUtils.isNotNull(roleId) && isAdmin(roleId)) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    @Override
    public boolean isAdmin(Long roleId) {
        return roleId != null && 1L == roleId;
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public Long countUserRoleByRoleId(Long roleId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserRole::getRoleId, roleId);
        return userRoleService.count(wrapper);
    }

    /**
     * 新增保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertRole(RoleParam role) {
        // 新增角色信息
        roleMapper.insertRole(role);
        return insertRoleMenu(role);
    }

    /**
     * 修改保存角色信息
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateRole(RoleParam role) {
        // 修改角色信息
        roleMapper.updateRole(role);
        deleteRoleMenuByRoleId(role.getRoleId());
        return insertRoleMenu(role);
    }

    private void deleteRoleMenuByRoleId(Long roleId) {
        // 删除角色与菜单关联
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RoleMenu::getRoleId, roleId);
        roleMenuService.remove(wrapper);
    }

    private void deleteRoleMenuByRoleIds(Long[] roleIds) {
        // 删除角色与菜单关联
        QueryWrapper<RoleMenu> wrapper = new QueryWrapper<>();
        wrapper.lambda().in(RoleMenu::getRoleId, roleIds);
        roleMenuService.remove(wrapper);
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public int updateRoleStatus(Role role) {
        return roleMapper.updateRole(role);
    }

    /**
     * 新增角色菜单信息
     *
     * @param role 角色对象
     */
    public Boolean insertRoleMenu(RoleParam role) {
        boolean result = false;
        // 新增用户与角色管理
        List<RoleMenu> list = new ArrayList<>();
        for (Long menuId : role.getMenuIds()) {
            RoleMenu rm = new RoleMenu();
            rm.setRoleId(role.getRoleId());
            rm.setMenuId(menuId);
            list.add(rm);
        }
        if (list.size() > 0) {
            result = roleMenuService.saveBatch(list);
        }
        return result;
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleById(Long roleId) {
        // 删除角色与菜单关联
        deleteRoleMenuByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            checkRoleAllowed(roleId);
            Role role = selectRoleById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        // 删除角色与菜单关联
        deleteRoleMenuByRoleIds(roleIds);
        return roleMapper.deleteRoleByIds(roleIds);
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public Boolean deleteAuthUser(UserRole userRole) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserRole::getRoleId, userRole.getRoleId()).eq(UserRole::getUserId, userRole.getUserId());
        return userRoleService.remove(wrapper);
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public Boolean deleteAuthUsers(Long roleId, Long[] userIds) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserRole::getRoleId, roleId).in(UserRole::getUserId, userIds);
        return userRoleService.remove(wrapper);
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public Boolean insertAuthUsers(Long roleId, Long[] userIds) {
        // 新增用户与角色管理
        List<UserRole> list = new ArrayList<>();
        for (Long userId : userIds) {
            UserRole ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleService.saveBatch(list);
    }
}
