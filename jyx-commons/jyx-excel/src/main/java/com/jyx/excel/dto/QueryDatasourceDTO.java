package com.jyx.excel.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @ClassName: QueryDatasourceDTO
 * @Description:
 * @Author: zls
 * @Date: 2024-03-06 17:14
 * @Version: 1.0
 **/
@Data
public class QueryDatasourceDTO {

    @NotNull
    private String datasourceId;

    @Size(min = 1, max = 2)
    private String name;
}



