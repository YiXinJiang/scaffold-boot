package com.jyx.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.excel.converters.localdatetime.LocalDateTimeDateConverter;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName: DatasourceExport
 * @Description: 导出模板
 * @Author: zls
 * @Date: 2024-03-06 17:07
 * @Version: 1.0
 **/
@Data
@HeadRowHeight(20)
@ContentRowHeight(15)
public class DatasourceExport {
    @ExcelProperty(value = "序号" ,index = 0)
    @ColumnWidth(15)
    private Long id;
    @ExcelProperty(value = "数据源名称" ,index = 1)
    @ColumnWidth(20)
    private String datasourceName;
    @ExcelProperty(value = "连接地址" ,index = 2)
    @ColumnWidth(20)
    private String url;
    @ExcelProperty(value = "用户名" ,index = 3)
    @ColumnWidth(20)
    private String username;
    @ExcelProperty(value = "密码" ,index = 4)
    @ColumnWidth(20)
    private String password;
    @ExcelProperty(value = "数据库驱动" ,index = 5)
    @ColumnWidth(20)
    private String driver;
    @ExcelProperty(value = "数据库类型" ,index = 6)
    @ColumnWidth(20)
    private String dbType;
    @ExcelProperty(value = "备注" ,index = 7)
    @ColumnWidth(20)
    private String comments;
    @ExcelProperty(value = "创建日期" ,index = 8, converter = LocalDateTimeDateConverter.class)
    @ColumnWidth(22)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}



