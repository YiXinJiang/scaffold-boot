package com.jyx.system.serviceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jyx.system.domain.Logininfor;
import com.jyx.system.mapper.LogininforMapper;
import com.jyx.system.param.QueryLoginLogParam;
import com.jyx.system.service.LogininforService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author tgb
 * @since 2024-03-12 03:15:33
 */
@Service
public class LogininforServiceImpl extends ServiceImpl<LogininforMapper, Logininfor> implements LogininforService {
    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(Logininfor logininfor) {
        baseMapper.insertLogininfor(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param param 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public IPage<Logininfor> selectLogininforList(QueryLoginLogParam param) {
        Page<Logininfor> page = new Page<>(param.getPageNum(), param.getPageSize());
        return baseMapper.selectLogininforList(page, param);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
        return baseMapper.deleteLogininforByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        baseMapper.cleanLogininfor();
    }
}
