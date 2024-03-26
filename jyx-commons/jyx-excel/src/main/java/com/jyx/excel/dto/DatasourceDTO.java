package com.jyx.excel.dto;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName: DatasourceDTO
 * @Description:
 * @Author: zls
 * @Date: 2024-03-06 17:14
 * @Version: 1.0
 **/
@Data
public class DatasourceDTO {

    Long id;

    String datasourceName;

    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}



