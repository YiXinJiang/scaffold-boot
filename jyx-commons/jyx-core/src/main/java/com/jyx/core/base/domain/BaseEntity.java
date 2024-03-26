package com.jyx.core.base.domain;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: BaseEntity
 * @Description:
 * @Author: tgb
 * @Date: 2024-03-04 13:50
 * @Version: 1.0
 **/
@Data
public class BaseEntity {
    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;
}
