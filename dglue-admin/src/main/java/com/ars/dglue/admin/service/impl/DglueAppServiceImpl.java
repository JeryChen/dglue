package com.ars.dglue.admin.service.impl;

import com.ars.dglue.admin.dao.DglueAppDAO;
import com.ars.dglue.admin.model.DglueApp;
import com.ars.dglue.admin.query.DglueAppQuery;
import com.ars.dglue.admin.service.DglueAppService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 〈应用信息 Service〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-10-21
 */
@Service
public class DglueAppServiceImpl implements DglueAppService {

    @Autowired
    private DglueAppDAO dglueAppDAO;

    @Override
    public DglueApp save(DglueApp record) {
        Assert.notNull(record, "待插入记录不能为空");
        record.setCreateTime(new Date());
        dglueAppDAO.insert(record);
        return record;
    }

    @Override
    public DglueApp getById(Long id) {
        Assert.notNull(id, "主键不能为空");
        return dglueAppDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(DglueApp record) {
        Assert.notNull(record, "待更新记录不能为空");
        record.setModifyTime(new Date());
        return dglueAppDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<DglueApp> listByCondition(DglueAppQuery query) {
        Assert.notNull(query, "查询条件不能为空");
        return dglueAppDAO.selectByCondition(query);
    }

    @Override
    public int countByCondition(DglueAppQuery query) {
        Assert.notNull(query, "查询条件不能为空");
        return dglueAppDAO.countByCondition(query);
    }

    @Override
    public int delete(Long id) {
        Assert.notNull(id, "待删除记录id不能为空");
        return dglueAppDAO.delete(id);
    }
}