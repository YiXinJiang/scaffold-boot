package com.jyx.business.system.controller;

import com.jyx.business.system.entity.AjaxResult;
import com.jyx.business.system.entity.BaseController;
import com.jyx.core.base.domain.R;
import com.jyx.core.constants.UserConstants;
import com.jyx.core.utils.StringUtils;
import com.jyx.system.domain.Menu;
import com.jyx.system.service.MenuService;
import com.jyx.system.vo.MenuVo;
import com.jyx.system.vo.TreeSelect;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单信息
 */
@Tag(name = "菜单信息接口")
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    @Resource
    private MenuService menuService;

    /**
     * 获取菜单列表
     */
    @Operation(summary = "获取菜单列表")
    @GetMapping("/list")
    public R<List<Menu>> list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu, getUserId());
        return R.ok(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @Operation(summary = "根据菜单编号获取详细信息")
    @GetMapping(value = "/{menuId}")
    public R<Menu> getInfo(@PathVariable Long menuId) {
        return R.ok(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @Operation(summary = "获取菜单下拉树列表")
    @GetMapping("/treeselect")
    public R<List<TreeSelect>> treeselect(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu, getUserId());
        return R.ok(menuService.buildMenuTreeSelect(transfer(menus)));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @Operation(summary = "加载对应角色菜单列表树")
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<Menu> menus = menuService.selectMenuList(getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(transfer(menus)));
        return ajax;
    }

    /**
     * 新增菜单
     */
    @Operation(summary = "新增菜单")
    @PostMapping
    public R<? extends Object> add(@Valid @RequestBody Menu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return R.fail("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(getUsername());
        return R.ok(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @Operation(summary = "修改菜单")
    @PutMapping
    public R<? extends Object> edit(@Valid @RequestBody Menu menu) {
        if (!menuService.checkMenuNameUnique(menu)) {
            return R.fail("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return R.fail("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return R.fail("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(getUsername());
        return R.ok(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @Operation(summary = "删除菜单")
    @DeleteMapping("/{menuId}")
    public R remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return R.fail("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return R.fail("菜单已分配,不允许删除");
        }
        return R.ok(menuService.deleteMenuById(menuId));
    }

    private List<MenuVo> transfer(List<Menu> menuList) {
        List<MenuVo> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(menuList)) {
            return list;
        }
        for (Menu menu : menuList) {
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(menu, menuVo);
            list.add(menuVo);
        }
        return list;
    }
}