package com.jyx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jyx.system.domain.RoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和菜单关联表 Mapper 接口
 * </p>
 *
 * @author tgb
 * @since 2024-03-04 06:04:34
 */
@Mapper
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

}
