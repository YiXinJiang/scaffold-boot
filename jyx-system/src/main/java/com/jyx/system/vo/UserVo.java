package com.jyx.system.vo;

import com.jyx.system.domain.Role;
import com.jyx.system.domain.User;
import lombok.Data;

import java.util.List;

/**
 * @ClassName: UserVo
 * @Description: 用户对象
 * @Author: tgb
 * @Date: 2024-03-05 14:32
 * @Version: 1.0
 **/
@Data
public class UserVo extends User {
    /**
     * 角色对象
     */
    private List<Role> roles;

    /**
     * 角色组
     */
    private Long[] roleIds;
}
