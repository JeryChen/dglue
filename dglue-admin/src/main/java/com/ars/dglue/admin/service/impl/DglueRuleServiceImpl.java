package com.ars.dglue.admin.service.impl;

import com.ars.dglue.admin.dao.DglueRuleDAO;
import com.ars.dglue.admin.model.DglueRule;
import com.ars.dglue.admin.query.DglueRuleQuery;
import com.ars.dglue.admin.service.DglueRuleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 〈逻辑规信息 Service〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-10-18
 */
@Service
public class DglueRuleServiceImpl implements DglueRuleService {

    @Autowired
    private DglueRuleDAO dglueRuleDAO;

    @Override
    public DglueRule save(DglueRule record) {
        Assert.notNull(record, "待插入记录不能为空");
        record.setCreateTime(new Date());
        dglueRuleDAO.insert(record);
        return record;
    }

    @Override
    public DglueRule getById(Long id) {
        Assert.notNull(id, "主键不能为空");
        return dglueRuleDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateById(DglueRule record) {
        Assert.notNull(record, "待更新记录不能为空");
        record.setModifyTime(new Date());
        return dglueRuleDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<DglueRule> listByCondition(DglueRuleQuery query) {
        Assert.notNull(query, "查询条件不能为空");
        return dglueRuleDAO.selectByCondition(query);
    }

    @Override
    public int countByCondition(DglueRuleQuery query) {
        Assert.notNull(query, "查询条件不能为空");
        return dglueRuleDAO.countByCondition(query);
    }

    @Override
    public int delete(Long id) {
        Assert.notNull(id, "待删除记录id不能为空");
        return dglueRuleDAO.delete(id);
    }
}