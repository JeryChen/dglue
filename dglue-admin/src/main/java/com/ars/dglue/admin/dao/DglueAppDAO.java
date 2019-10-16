package com.ars.dglue.admin.dao;

import com.ars.dglue.admin.model.DglueApp;
import com.ars.dglue.admin.query.DglueAppQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 〈配置应用信息 DAO〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-09-29
 */
@Mapper
@Repository
public interface DglueAppDAO {

    /**
     * 新增一条记录
     *
     * @param record 保存对象
     */
    int insert(DglueApp record);

    /**
     * 根据主键查询
     *
     * @param id 数据库主键
     */
    DglueApp selectByPrimaryKey(Long id);

    /**
     * 根据主键更新数据
     *
     * @param record 更新参数
     */
    int updateByPrimaryKeySelective(DglueApp record);

    /**
     * 根据查询参数查询数据
     *
     * @param query 查询参数
     */
    List<DglueApp> selectByCondition(DglueAppQuery query);

    /**
     * 根据查询参数查询数据总量
     *
     * @param query 查询参数
     */
    Integer countByCondition(DglueAppQuery query);

}