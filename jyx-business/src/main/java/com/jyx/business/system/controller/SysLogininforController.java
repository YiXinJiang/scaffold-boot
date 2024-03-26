package com.jyx.business.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyx.authentication.service.SysPasswordService;
import com.jyx.business.system.entity.BaseController;
import com.jyx.business.system.entity.TableDataInfo;
import com.jyx.core.base.domain.R;
import com.jyx.system.domain.Logininfor;
import com.jyx.system.param.QueryLoginLogParam;
import com.jyx.system.service.LogininforService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: SysLogininforController
 * @Description: 系统访问记录
 * @Author: tgb
 * @Date: 2024-03-12 15:18
 * @Version: 1.0
 **/
@Tag(name = "系统访问记录接口")
@RestController
@RequestMapping("/monitor/logininfor")
public class SysLogininforController extends BaseController {
    @Autowired
    private LogininforService logininforService;

    @Autowired
    private SysPasswordService passwordService;

    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public TableDataInfo list(QueryLoginLogParam param) {
        IPage<Logininfor> list = logininforService.selectLogininforList(param);
        return getDataTable(list);
    }

    @Operation(summary = "删除数据")
    @DeleteMapping("/{infoIds}")
    public R<Integer> remove(@PathVariable Long[] infoIds) {
        return R.ok(logininforService.deleteLogininforByIds(infoIds));
    }

    @Operation(summary = "清空数据")
    @DeleteMapping("/clean")
    public R clean() {
        logininforService.cleanLogininfor();
        return R.ok();
    }

    @Operation(summary = "解锁")
    @GetMapping("/unlock/{userName}")
    public R unlock(@PathVariable("userName") String userName) {
        passwordService.clearLoginRecordCache(userName);
        return R.ok();
    }
}
