package com.jyx.business.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyx.business.system.entity.BaseController;
import com.jyx.business.system.entity.TableDataInfo;
import com.jyx.core.base.domain.R;
import com.jyx.system.domain.DictType;
import com.jyx.system.service.DictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据字典信息
 */
@Tag(name = "数据字典类型信息接口")
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {
    @Autowired
    private DictTypeService dictTypeService;

    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public TableDataInfo list(DictType dictType) {
        IPage<DictType> list = dictTypeService.selectDictTypePage(dictType);
        return getDataTable(list);
    }

    /**
     * 查询字典类型详细
     */
    @Operation(summary = "查询字典类型详细")
    @GetMapping(value = "/{dictId}")
    public R<DictType> getInfo(@PathVariable Long dictId) {
        return R.ok(dictTypeService.selectDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @Operation(summary = "新增字典类型")
    @PostMapping
    public R<Integer> add(@Valid @RequestBody DictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return R.fail("新增字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setCreateBy(getUsername());
        return R.ok(dictTypeService.insertDictType(dict));
    }

    /**
     * 修改字典类型
     */
    @Operation(summary = "修改字典类型")
    @PutMapping
    public R edit(@Valid @RequestBody DictType dict) {
        if (!dictTypeService.checkDictTypeUnique(dict)) {
            return R.fail("修改字典'" + dict.getDictName() + "'失败，字典类型已存在");
        }
        dict.setUpdateBy(getUsername());
        return R.ok(dictTypeService.updateDictType(dict));
    }

    /**
     * 删除字典类型
     */
    @Operation(summary = "删除字典类型")
    @DeleteMapping("/{dictIds}")
    public R remove(@PathVariable Long[] dictIds) {
        dictTypeService.deleteDictTypeByIds(dictIds);
        return R.ok();
    }

    /**
     * 获取字典选择框列表
     */
    @Operation(summary = "获取字典选择框列表")
    @GetMapping("/optionselect")
    public R<List<DictType>> optionselect() {
        List<DictType> dictTypes = dictTypeService.selectDictTypeAll();
        return R.ok(dictTypes);
    }
}
