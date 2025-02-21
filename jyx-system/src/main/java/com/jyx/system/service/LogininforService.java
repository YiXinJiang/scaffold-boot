package com.jyx.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jyx.system.domain.Logininfor;
import com.jyx.system.param.QueryLoginLogParam;

/**
 * <p>
 * 系统访问记录 服务类
 * </p>
 *
 * @author tgb
 * @since 2024-03-12 03:15:33
 */
public interface LogininforService extends IService<Logininfor> {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    void insertLogininfor(Logininfor logininfor);

    /**
     * 查询系统登录日志集合
     *
     * @param param 访问日志对象
     * @return 登录记录集合
     */
    IPage<Logininfor> selectLogininforList(QueryLoginLogParam param);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     */
    void cleanLogininfor();
}
