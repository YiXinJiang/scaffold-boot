package com.jyx.system.vo;

import com.jyx.system.domain.Menu;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: MenuVO
 * @Description:
 * @Author: tgb
 * @Date: 2024-03-05 10:27
 * @Version: 1.0
 **/
@ToString
public class MenuVo extends Menu {

    /**
     * 父菜单名称
     */
    private String parentName;

    /**
     * 是否为外链（0是 1否）
     */
    private String isFrame;

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    private String isCache;

    /**
     * 子菜单
     */
    private List<MenuVo> children = new ArrayList<>();

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setIsFrame(String isFrame) {
        this.isFrame = isFrame;
    }

    public void setIsCache(String isCache) {
        this.isCache = isCache;
    }

    public List<MenuVo> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVo> children) {
        this.children = children;
    }
}
