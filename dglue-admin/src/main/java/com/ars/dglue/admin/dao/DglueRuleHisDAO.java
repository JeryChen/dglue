package com.ars.dglue.admin.dao;

import com.ars.dglue.admin.model.DglueRuleHis;
import com.ars.dglue.admin.query.DglueRuleHisQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈逻辑规则历史版本信息 DAO〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-09-29
 */
@Repository
@Mapper
public interface DglueRuleHisDAO {

    /**
     * 新增一条记录
     *
     * @param record 保存对象
     */
    int insert(DglueRuleHis record);

    /**
     * 根据主键查询
     *
     * @param id 数据库主键
     */
    DglueRuleHis selectByPrimaryKey(Long id);

    /**
     * 根据主键更新数据
     *
     * @param record 更新参数
     */
    int updateByPrimaryKeySelective(DglueRuleHis record);

    /**
     * 根据查询参数查询数据
     *
     * @param query 查询参数
     */
    List<DglueRuleHis> selectByCondition(DglueRuleHisQuery query);

    /**
     * 根据查询参数查询数据总量
     *
     * @param query 查询参数
     */
    Integer countByCondition(DglueRuleHisQuery query);

}