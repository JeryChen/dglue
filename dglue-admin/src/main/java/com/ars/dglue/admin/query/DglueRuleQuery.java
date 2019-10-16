package com.ars.dglue.admin.query;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈逻辑规则信息 查询参数〉<p>
 *
 * @author
 * @date 2019-09-29
 */
@lombok.Data
@lombok.ToString(callSuper = true)
public class DglueRuleQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编号
     */
    private String appCode;

    /**
     * 规则编号
     */
    private String ruleCode;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 源码
     */
    private String source;

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