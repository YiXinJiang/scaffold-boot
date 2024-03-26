package com.jyx.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @ClassName: PageEntity
 * @Description: 分页参数
 * @Author: tgb
 * @Date: 2024-03-06 09:58
 * @Version: 1.0
 **/
@Data
public class PageEntity {
    /**
     * 每页数据
     */
    @TableField(exist = false)
    private Integer pageSize;

    /**
     * 当前页
     */
    @TableField(exist = false)
    private Integer pageNum;

    @TableField(exist = false)
    @Schema(title="时间范围搜索-开始")
    private LocalDate beginTime;

    @TableField(exist = false)
    @Schema(title="时间范围搜索-结束")
    private LocalDate endTime;
}
