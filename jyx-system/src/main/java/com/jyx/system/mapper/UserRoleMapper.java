package com.jyx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jyx.system.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author tgb
 * @since 2024-03-04 06:04:34
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
