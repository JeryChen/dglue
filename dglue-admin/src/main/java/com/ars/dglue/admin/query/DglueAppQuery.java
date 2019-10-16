package com.ars.dglue.admin.query;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈配置应用信息 查询参数〉<p>
 *
 * @author
 * @date 2019-09-29
 */
@lombok.Data
@lombok.ToString(callSuper = true)
public class DglueAppQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用code
     */
    private String appCode;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 创建时间 开始
     */
    private Date createTimeFrom;

    /**
     * 创建时间 结束
     */
    private Date createTimeTo;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改时间 开始
     */
    private Date modifyTimeFrom;

    /**
     * 修改时间 结束
     */
    private Date modifyTimeTo;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 索引校验
     */
    public boolean checkIndex(){
        boolean result = false
                || null != this.appCode;
        return result;
    }
}