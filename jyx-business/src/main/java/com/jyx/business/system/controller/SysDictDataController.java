package com.jyx.business.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyx.business.system.entity.BaseController;
import com.jyx.business.system.entity.TableDataInfo;
import com.jyx.core.base.domain.R;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.domain.DictData;
import com.jyx.system.service.DictDataService;
import com.jyx.system.service.DictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典信息
 */
@Tag(name = "数据字典信息接口")
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {
    @Autowired
    private DictDataService dictDataService;

    @Autowired
    private DictTypeService dictTypeService;

    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public TableDataInfo<DictData> list(DictData dictData) {
        IPage<DictData> dictDataIPage = dictDataService.selectDictDataPage(dictData);
        return getDataTable(dictDataIPage);
    }

    /**
     * 查询字典数据详细
     */
    @Operation(summary = "查询字典数据详细")
    @GetMapping(value = "/{dictCode}")
    public R<DictData> getInfo(@PathVariable Long dictCode) {
        return R.ok(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @Operation(summary = "根据字典类型查询字典数据信息")
    @GetMapping(value = "/type/{dictType}")
    public R<List<DictData>> dictType(@PathVariable String dictType) {
        List<DictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data)) {
            data = new ArrayList<>();
        }
        return R.ok(data);
    }

    /**
     * 新增字典类型
     */
    @Operation(summary = "新增字典类型")
    @PostMapping
    public R<Integer> add(@Valid @RequestBody DictData dict) {
        dict.setCreateBy(getUsername());
        return R.ok(dictDataService.insertDictData(dict));
    }

    /**
     * 修改保存字典类型
     */
    @Operation(summary = "修改保存字典类型")
    @PutMapping
    public R<Integer> edit(@Valid @RequestBody DictData dict) {
        dict.setUpdateBy(getUsername());
        return R.ok(dictDataService.updateDictData(dict));
    }

    /**
     * 删除字典类型
     */
    @Operation(summary = "删除字典类型")
    @DeleteMapping("/{dictCodes}")
    public R remove(@PathVariable Long[] dictCodes) {
        dictDataService.deleteDictDataByIds(dictCodes);
        return R.ok();
    }
}
