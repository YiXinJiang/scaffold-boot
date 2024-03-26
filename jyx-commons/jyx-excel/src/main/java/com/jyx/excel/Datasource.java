package com.jyx.excel;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName: Datasource
 * @Description:
 * @Author: zls
 * @Date: 2024-03-06 17:16
 * @Version: 1.0
 **/
@Data
public class Datasource {
    int id;

    String dataSourceName;

    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}



