package com.ars.dglue.admin.process;

import com.ars.dglue.admin.vo.RuleVo;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-24.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface RuleProcess {

    /**
     * add new rule
     *
     * @param ruleVo rule params
     */
    void add(RuleVo ruleVo);

    /**
     * update one rule info
     *
     * @param ruleVo rule params
     */
    void update(RuleVo ruleVo);

    /**
     * delete one rule info
     *
     * @param ruleId rule id
     */
    void delete(Long ruleId);

    /**
     * test java code is not true
     *
     * @param source code
     */
    void test(String source);

    /**
     * publish rule
     *
     * @param id rule id
     */
    void publish(Long id);
}
