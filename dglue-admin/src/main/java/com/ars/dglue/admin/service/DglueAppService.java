package com.ars.dglue.admin.service;

import com.ars.dglue.admin.model.DglueApp;
import com.ars.dglue.admin.query.DglueAppQuery;

import java.util.List;

/**
 * 〈配置应用信息 Service〉<p>
 * 〈功能详细描述〉
 *
 * @author
 * @date 2019-09-29
 */
public interface DglueAppService {

    /**
     * 新增一条记录
     *
     * @param record 保存对象
     */
    DglueApp save(DglueApp record);

    /**
     * 根据ID查询
     *
     * @param id 数据库ID
     */
    DglueApp getById(Long id);

    /**
     * 根据id更新一条数据
     *
     * @param record 更新参数
     */
    int updateById(DglueApp record);

    /**
     * 根据查询参数查询数据
     *
     * @param query 查询参数
     */
    List<DglueApp> listByCondition(DglueAppQuery query);

    /**
     * 根据查询参数查询数据总量
     *
     * @param query 查询参数
     */
    int countByCondition(DglueAppQuery query);
}