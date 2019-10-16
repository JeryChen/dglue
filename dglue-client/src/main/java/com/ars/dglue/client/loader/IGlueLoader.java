package com.ars.dglue.client.loader;

import java.util.List;

/**
 * 〈一句话介绍功能〉<br>
 * glue loader
 *
 * @author jierui on 2019-10-08.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface IGlueLoader {

    /**
     * load glue by appCode and ruleCode
     *
     * @param appCode  app code
     * @param ruleCode rule code
     * @return glue code
     */
    String load(String appCode, String ruleCode);

    /**
     * load glue by appCode
     *
     * @param appCode app code
     * @return glue code
     */
    List<String> load(String appCode);
}
