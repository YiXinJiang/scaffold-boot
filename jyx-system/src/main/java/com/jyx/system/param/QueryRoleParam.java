package com.jyx.system.param;

import com.jyx.system.entity.PageEntity;
import lombok.Data;

/**
 * @ClassName: RoleQueryParam
 * @Description: 角色查询信息入参
 * @Author: tgb
 * @Date: 2024-03-05 15:53
 * @Version: 1.0
 **/
@Data
public class QueryRoleParam extends PageEntity {
    private Long roleId;

    private String roleName;

    private String status;

    private String roleKey;
}
