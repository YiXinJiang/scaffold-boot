package com.jyx.business.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jyx.business.system.entity.BaseController;
import com.jyx.business.system.entity.TableDataInfo;
import com.jyx.core.base.domain.R;
import com.jyx.system.constants.ConfigConstants;
import com.jyx.system.domain.Config;
import com.jyx.system.service.ConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 参数配置 信息操作处理
 */
@Tag(name = "参数配置 信息操作处理接口")
@RestController
@RequestMapping("/system/config")
public class SysConfigController extends BaseController {

    @Resource
    private ConfigService configService;

    /**
     * 获取参数配置列表
     */
    @Operation(summary = "分页查询")
    @GetMapping("/list")
    public TableDataInfo<Config> list(Config config) {
        IPage<Config> list = configService.selectConfigPage(config);
        return getDataTable(list);
    }

    /**
     * 根据参数编号获取详细信息
     */
    @Operation(summary = "根据参数编号获取详细信息")
    @GetMapping(value = "/{configId}")
    public R<Config> getInfo(@PathVariable Long configId) {
        return R.ok(configService.selectConfigById(configId));
    }

    /**
     * 根据参数键名查询参数值
     */
    @Operation(summary = "根据参数键名查询参数值")
    @GetMapping(value = "/configKey/{configKey}")
    public R<String> getConfigKey(@PathVariable String configKey) {
        return R.ok(configService.selectConfigByKey(configKey));
    }

    /**
     * 获取注册信息value
     *
     * @author tgb
     * @date 2024/3/11 17:19
     *
     * @return R<String>
     */
    @Operation(summary = "获取注册信息value")
    @GetMapping("/getRegisterValue")
    public R<String> getRegisterValue() {
        return R.ok(configService.selectConfigByKey(ConfigConstants.REGISTRY_CONFIG_KEY));
    }

    /**
     * 新增参数配置
     */
    @Operation(summary = "新增参数配置")
    @PostMapping
    public R<Integer> add(@Valid @RequestBody Config config) {
        if (!configService.checkConfigKeyUnique(config)) {
            return R.fail("新增参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setCreateBy(getUsername());
        return R.ok(configService.insertConfig(config));
    }

    /**
     * 修改参数配置
     */
    @Operation(summary = "修改参数配置")
    @PutMapping
    public R<Integer> edit(@Valid @RequestBody Config config) {
        if (!configService.checkConfigKeyUnique(config)) {
            return R.fail("修改参数'" + config.getConfigName() + "'失败，参数键名已存在");
        }
        config.setUpdateBy(getUsername());
        return R.ok(configService.updateConfig(config));
    }

    /**
     * 删除参数配置
     */
    @Operation(summary = "删除参数配置")
    @DeleteMapping("/{configIds}")
    public R remove(@PathVariable Long[] configIds) {
        configService.deleteConfigByIds(configIds);
        return R.ok();
    }

    /**
     * 刷新参数缓存
     */
    @Operation(summary = "刷新参数缓存")
    @DeleteMapping("/refreshCache")
    public R refreshCache() {
        configService.resetConfigCache();
        return R.ok();
    }
}
