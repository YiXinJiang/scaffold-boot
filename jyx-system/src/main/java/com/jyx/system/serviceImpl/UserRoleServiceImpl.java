package com.jyx.system.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.system.domain.UserRole;
import com.jyx.system.mapper.UserRoleMapper;
import com.jyx.system.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author tgb
 * @since 2024-03-04 06:04:34
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
