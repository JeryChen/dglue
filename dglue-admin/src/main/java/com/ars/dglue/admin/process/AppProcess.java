package com.ars.dglue.admin.process;

import com.ars.dglue.admin.vo.AppVo;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-22.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public interface AppProcess {

    /**
     * add new app
     *
     * @param appVo app params
     */
    void add(AppVo appVo);

    /**
     * update one app
     *
     * @param appVo app params
     */
    void update(AppVo appVo);

    /**
     * delete one app
     *
     * @param id app id
     */
    void delete(Long id);
}
