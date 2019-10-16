package com.ars.dglue.admin.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈配置应用信息〉<p>
 *
 * @author
 * @date 2019-09-29
 */
@Data
@ToString
public class DglueApp implements Serializable {

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
     * 应用code
     *
     * @最大长度   20
     * @允许为空   NO
     * @是否索引   YES
     * @唯一索引   uk_app_code
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