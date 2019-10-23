package com.ars.dglue.admin.service.impl;

import com.ars.dglue.admin.dao.DglueRuleHisDAO;
import com.ars.dglue.admin.model.DglueRuleHis;
import com.ars.dglue.admin.query.DglueRuleHisQuery;
import com.ars.dglue.admin.service.DglueRuleHisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 〈历史逻辑规则信息 Service〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-10-18
 */
@Service
public class DglueRuleHisServiceImpl implements DglueRuleHisService {

    @Autowired
    private DglueRuleHisDAO dglueRuleHisDAO;

    @Override
    public DglueRuleHis save(DglueRuleHis record) {
        Assert.notNull(record, "待插入记录不能为空");
        record.setCreateTime(new Date());
        dglueRuleHisDAO.insert(record);
        return record;
    }

    @Override
    public DglueRuleHis getById(Long id) {
        Assert.notNull(id, "主键不能为空");
        return dglueRuleHisDAO.selectByPrimaryKey(id);
    }

    @Override
    public List<DglueRuleHis> listByCondition(DglueRuleHisQuery query) {
        Assert.notNull(query, "查询条件不能为空");
        return dglueRuleHisDAO.selectByCondition(query);
    }

    @Override
    public int countByCondition(DglueRuleHisQuery query) {
        Assert.notNull(query, "查询条件不能为空");
        return dglueRuleHisDAO.countByCondition(query);
    }
}