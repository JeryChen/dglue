package com.ars.dglue.admin.model;

import java.util.Date;

import java.io.Serializable;

/**
 * 〈逻辑规则历史版本信息〉<p>
 *
 * @author
 * @date 2019-09-29
 */
@lombok.Data
@lombok.ToString
public class DglueRuleHis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     *
     * @允许为空   NO
     * @是否索引   YES
     * @唯一索引   PRIMARY
     */
    private Long id;

    /**
     * 规则Id
     *
     * @允许为空   NO
     * @是否索引   YES
     * @唯一索引   uk_rule_id_version
     */
    private Long ruleId;

    /**
     * 应用编号
     *
     * @最大长度   20
     * @允许为空   NO
     * @是否索引   NO
     */
    private String appCode;

    /**
     * 编号
     *
     * @最大长度   32
     * @允许为空   NO
     * @是否索引   NO
     */
    private String code;

    /**
     * 名称
     *
     * @最大长度   32
     * @允许为空   NO
     * @是否索引   NO
     */
    private String name;

    /**
     * 版本
     *
     * @允许为空   NO
     * @是否索引   NO
     */
    private Integer version;

    /**
     * 源码
     *
     * @最大长度   65535
     * @允许为空   NO
     * @是否索引   NO
     */
    private String source;

    /**
     * 创建时间
     *
     * @允许为空   NO
     * @是否索引   NO
     */
    private Date createTime;

    /**
     * 创建人
     *
     * @最大长度   20
     * @允许为空   NO
     * @是否索引   NO
     */
    private String creator;


}