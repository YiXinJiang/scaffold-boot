package com.jyx.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jyx.system.domain.Logininfor;
import com.jyx.system.param.QueryLoginLogParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统访问记录 Mapper 接口
 * </p>
 *
 * @author tgb
 * @since 2024-03-12 03:15:33
 */
@Mapper
public interface LogininforMapper extends BaseMapper<Logininfor> {

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
     * @param page       分页数据
     * @return 登录记录集合
     */
    IPage<Logininfor> selectLogininforList(Page<Logininfor> page, @Param("param") QueryLoginLogParam param);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    int deleteLogininforByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLogininfor();
}
