package com.jyx.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.jyx.system.entity.PageEntity;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * <p>
 * 参数配置表
 * </p>
 *
 * @author tgb
 * @since 2024-03-04 06:04:34
 */
@Data
@ToString
@TableName("sys_config")
public class Config extends PageEntity {
    /**
     * 参数主键
     */
    @TableId(value = "config_id")
    private Long configId;

    /**
     * 参数名称
     */
    @NotBlank(message = "参数名称不能为空")
    @Size(min = 0, max = 100, message = "参数名称不能超过100个字符")
    @TableField("config_name")
    private String configName;

    /**
     * 参数键名
     */
    @NotBlank(message = "参数键名长度不能为空")
    @Size(min = 0, max = 100, message = "参数键名长度不能超过100个字符")
    @Pattern(regexp ="^[^\\u4e00-\\u9fa5]+$", message = "参数键名不能有汉字")
    @TableField("config_key")
    private String configKey;

    /**
     * 参数键值
     */
    @NotBlank(message = "参数键值不能为空")
    @Size(min = 0, max = 500, message = "参数键值长度不能超过500个字符")
    @Pattern(regexp ="^[^\\u4e00-\\u9fa5]+$", message = "参数键值不能有汉字")
    @TableField("config_value")
    private String configValue;

    /**
     * 系统内置（Y是 N否）
     */
    @TableField("config_type")
    private String configType;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}
