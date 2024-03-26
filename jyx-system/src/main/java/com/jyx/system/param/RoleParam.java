package com.jyx.system.param;

import com.jyx.system.domain.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @ClassName: RoleParam
 * @Description: 角色信息入参
 * @Author: tgb
 * @Date: 2024-03-05 15:49
 * @Version: 1.0
 **/
@Data
public class RoleParam extends Role {
    /** 菜单组 */
    private Long[] menuIds;

    /** 角色菜单权限 */
    private Set<String> permissions;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;
}
