package com.jyx.system.param;

import com.jyx.system.entity.PageEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @ClassName: UserParam
 * @Description: 用户搜索条件入参
 * @Author: tgb
 * @Date: 2024-03-05 14:49
 * @Version: 1.0
 **/
@Data
@Schema(title = "用户搜索条件入参")
public class UserParam extends PageEntity {

    @Schema(title="用户id")
    private Long userId;

    @Schema(title="用户名称")
    private String userName;

    @Schema(title="用户状态")
    private String status;

    @Schema(title="用户手机号码")
    private String phonenumber;

    @Schema(title="角色id")
    private Long roleId;
}
