package com.ars.dglue.admin.service;

import com.ars.dglue.admin.model.DglueRuleHis;
import com.ars.dglue.admin.query.DglueRuleHisQuery;

import java.util.List;

/**
 * 〈逻辑规则历史版本信息 Service〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-09-29
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
     * 根据id更新一条数据
     *
     * @param record 更新参数
     */
    int updateById(DglueRuleHis record);

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