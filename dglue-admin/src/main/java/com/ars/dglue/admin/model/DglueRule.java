package com.ars.dglue.admin.model;

import java.util.Date;
import java.util.Date;

import java.io.Serializable;

/**
 * 〈逻辑规信息〉<p>
 *
 * @author
 * @date 2019-10-18
 */
@lombok.Data
@lombok.ToString
public class DglueRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     *
     * @允许为空   NO
     * @是否索引   YES
     * @唯一索引   PRIMARY
     */
    private Long id;

    /**
     * 应用编号
     *
     * @最大长度   20
     * @允许为空   NO
     * @是否索引   YES
     * @唯一索引   uk_app_code_rule_code
     */
    private String appCode;

    /**
     * 应用名称
     *
     * @最大长度   32
     * @允许为空   NO
     * @是否索引   NO
     */
    private String appName;

    /**
     * 规则编号
     *
     * @最大长度   32
     * @允许为空   NO
     * @是否索引   NO
     */
    private String ruleCode;

    /**
     * 规则名称
     *
     * @最大长度   32
     * @允许为空   NO
     * @是否索引   NO
     */
    private String ruleName;

    /**
     * 类型 1 逻辑校验 2 逻辑处理
     *
     * @允许为空   NO
     * @是否索引   NO
     */
    private Integer ruleType;

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

    /**
     * 修改时间
     *
     * @允许为空   YES
     * @是否索引   NO
     */
    private Date modifyTime;

    /**
     * 修改人
     *
     * @最大长度   20
     * @允许为空   YES
     * @是否索引   NO
     */
    private String modifier;


}