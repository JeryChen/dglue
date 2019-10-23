package com.ars.dglue.admin.service;

import com.ars.dglue.admin.model.DglueRule;
import com.ars.dglue.admin.query.DglueRuleQuery;

import java.util.List;

/**
 * 〈逻辑规信息 Service〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-10-18
 */
public interface DglueRuleService {

    /**
     * 新增一条记录
     *
     * @param record 保存对象
     */
    DglueRule save(DglueRule record);

    /**
     * 根据ID查询
     *
     * @param id 数据库ID
     */
    DglueRule getById(Long id);

    /**
     * 根据id更新一条数据
     *
     * @param record 更新参数
     */
    int updateById(DglueRule record);

    /**
     * 根据查询参数查询数据
     *
     * @param query 查询参数
     */
    List<DglueRule> listByCondition(DglueRuleQuery query);

    /**
     * 根据查询参数查询数据总量
     *
     * @param query 查询参数
     */
    int countByCondition(DglueRuleQuery query);

    /**
     * 根据主键id删除
     *
     * @param id 主键id
     * @return 结果
     */
    int delete(Long id);
}