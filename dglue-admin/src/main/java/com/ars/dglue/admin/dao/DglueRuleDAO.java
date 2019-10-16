package com.ars.dglue.admin.dao;

import com.ars.dglue.admin.model.DglueRule;
import com.ars.dglue.admin.query.DglueRuleQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈逻辑规则信息 DAO〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-09-29
 */
@Repository
@Mapper
public interface DglueRuleDAO {

    /**
     * 新增一条记录
     *
     * @param record 保存对象
     */
    int insert(DglueRule record);

    /**
     * 根据主键查询
     *
     * @param id 数据库主键
     */
    DglueRule selectByPrimaryKey(Long id);

    /**
     * 根据主键更新数据
     *
     * @param record 更新参数
     */
    int updateByPrimaryKeySelective(DglueRule record);

    /**
     * 根据查询参数查询数据
     *
     * @param query 查询参数
     */
    List<DglueRule> selectByCondition(DglueRuleQuery query);

    /**
     * 根据查询参数查询数据总量
     *
     * @param query 查询参数
     */
    Integer countByCondition(DglueRuleQuery query);

}