package com.ars.dglue.admin.service;

import com.ars.dglue.admin.model.DglueRuleHis;
import com.ars.dglue.admin.query.DglueRuleHisQuery;

import java.util.List;

/**
 * 〈历史逻辑规则信息 Service〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-10-18
 */
public interface DglueRuleHisService {

    /**
     * 新增一条记录
     *
     * @param record 保存对象
     */
    DglueRuleHis save(DglueRuleHis record);

    /**
     * 根据ID查询
     *
     * @param id 数据库ID
     */
    DglueRuleHis getById(Long id);

    /**
     * 根据查询参数查询数据
     *
     * @param query 查询参数
     */
    List<DglueRuleHis> listByCondition(DglueRuleHisQuery query);

    /**
     * 根据查询参数查询数据总量
     *
     * @param query 查询参数
     */
    int countByCondition(DglueRuleHisQuery query);
}