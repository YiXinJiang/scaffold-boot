package com.jyx.business.controller;

import com.alibaba.excel.EasyExcel;
import com.jyx.core.base.domain.R;
import com.jyx.excel.Datasource;
import com.jyx.excel.DatasourceExport;
import com.jyx.excel.DatasourceImport;
import com.jyx.excel.dto.DatasourceDTO;
import com.jyx.excel.dto.QueryDatasourceDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName: DatasourceController
 * @Description: 批量导出、导入模板、上传数据
 * @Author: zls
 * @Date: 2024-03-06 17:03
 * @Version: 1.0
 **/
@RestController
public class DatasourceController {

    /**
     * 批量导出数据
     * @param response 响应
     * @param queryDatasourceDTO 查询条件
     * @throws IOException 异常
     */
    @PostMapping("/download")
    public void download(HttpServletResponse response,@Valid @RequestBody QueryDatasourceDTO queryDatasourceDTO) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("数据源列表", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<DatasourceDTO> dataSourceList = getDataSourceList();
        List<DatasourceExport> dataSourceExportList = new ArrayList<>();
        for (DatasourceDTO datasourceDTO : dataSourceList) {
            DatasourceExport datasourceExport = new DatasourceExport();
            BeanUtils.copyProperties(datasourceDTO, datasourceExport);
            dataSourceExportList.add(datasourceExport);
        }
        String sheetName = "数据源列表";
        EasyExcel.write(response.getOutputStream(), DatasourceExport.class).sheet(sheetName).doWrite(dataSourceExportList);
    }

    @NotNull
    private static List<DatasourceDTO> getDataSourceList() {
        List<DatasourceDTO> datasourceDTOList = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            DatasourceDTO datasourceDTO = new DatasourceDTO();
            datasourceDTO.setId(i);
            datasourceDTO.setDatasourceName("数据源名称" + i);
            datasourceDTO.setCreateTime(LocalDateTime.now());
            datasourceDTOList.add(datasourceDTO);
        }
        return datasourceDTOList;
    }

    /**
     * 下载导入模板
     * @param response 响应
     * @throws IOException 异常
     */
    @GetMapping("/download/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("数据源导入模板", StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        String sheetName = "数据源列表";
        EasyExcel.write(response.getOutputStream(), DatasourceImport.class).sheet(sheetName).doWrite((Collection<?>) null);
    }

    /**
     * 上传数据
     * @param file 文件
     * @return R
     * @throws IOException 异常
     */
    @PostMapping("/upload")
    public R<?> upload(@RequestParam("uploadFile") MultipartFile file) throws IOException {
        List<DatasourceImport> datasourceImportList =  EasyExcel.read(file.getInputStream(), DatasourceImport.class, null).sheet().doReadSync();
        if (!CollectionUtils.isEmpty(datasourceImportList))
        {
            List<Datasource> datasourceList = getDatasourceList();
            datasourceImportList.forEach(datasourceImport-> {
                Datasource datasource = new Datasource();
                BeanUtils.copyProperties(datasourceImport, datasource);
                datasourceList.add(datasource);
            });
            // 保存数据源
        }
        return R.ok();
    }

    @NotNull
    private static List<Datasource> getDatasourceList() {
        List<Datasource> datasources = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Datasource datasource = new Datasource();
            datasource.setId(i);
            datasource.setDataSourceName("数据源名称" + i);
            datasource.setCreateTime(LocalDateTime.now());
            datasources.add(datasource);
        }
        return datasources;
    }
}



