package com.ars.dglue.admin.query;

import com.ars.dglue.admin.utils.PagingQuery;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈逻辑规信息 查询参数〉<p>
 *
 * @author
 * @date 2019-10-18
 */
@lombok.Data
@lombok.ToString(callSuper = true)
public class DglueRuleQuery extends PagingQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 应用编号
     */
    private String appCode;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 规则编号
     */
    private String ruleCode;

    /**
     * 规则名称
     */
    private String ruleName;

    /**
     * 类型 1 逻辑校验 2 逻辑处理
     */
    private Integer ruleType;

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