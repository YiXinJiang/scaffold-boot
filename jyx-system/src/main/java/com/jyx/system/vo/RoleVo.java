package com.jyx.system.vo;

import com.jyx.system.domain.Role;
import lombok.Data;

import java.util.Set;

/**
 * @ClassName: RoleVo
 * @Description: Role返回值
 * @Author: tgb
 * @Date: 2024-03-05 16:10
 * @Version: 1.0
 **/
@Data
public class RoleVo extends Role {
    /** 菜单组 */
    private Long[] menuIds;

    /** 角色菜单权限 */
    private Set<String> permissions;

    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;
}
