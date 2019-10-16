package com.ars.dglue.admin.query;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈逻辑规则历史版本信息 查询参数〉<p>
 *
 * @author
 * @date 2019-09-29
 */
@lombok.Data
@lombok.ToString(callSuper = true)
public class DglueRuleHisQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规则Id
     */
    private Long ruleId;

    /**
     * 应用编号
     */
    private String appCode;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

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
     * 索引校验
     */
    public boolean checkIndex(){
        boolean result = false
                || null != this.ruleId;
        return result;
    }
}