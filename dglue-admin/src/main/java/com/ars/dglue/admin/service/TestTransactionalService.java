package com.ars.dglue.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-09-27.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
public class TestTransactionalService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Throwable.class)
    public boolean create() {
        insert(5L, "loanmng5", "管理5");
        if (null == null) {
            throw new RuntimeException("测试下事务啊");
        }
        insert(6L, "loanmng6", "管理6");
        return true;
    }

    public void insert(Long id, String appName, String name) {
        jdbcTemplate.update("insert into xxl_glue_project (id, appname, name) values (?, ?, ?) ",
                new Object[]{id, appName, name});
    }
}
