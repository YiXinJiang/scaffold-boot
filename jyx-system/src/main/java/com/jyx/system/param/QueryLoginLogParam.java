package com.jyx.system.param;

import com.jyx.system.entity.PageEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName: QueryLoginLogParam
 * @Description: 登录日志查询入参
 * @Author: tgb
 * @Date: 2024-03-12 15:33
 * @Version: 1.0
 **/
@Data
public class QueryLoginLogParam extends PageEntity {

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录状态（0成功 1失败）
     */
    private String status;


    /**
     * 访问时间
     */
    private LocalDateTime loginTime;
}
