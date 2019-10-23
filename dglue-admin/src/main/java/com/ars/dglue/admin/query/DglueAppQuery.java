package com.ars.dglue.admin.query;

import com.ars.dglue.admin.utils.PagingQuery;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈应用信息 查询参数〉<p>
 *
 * @author
 * @date 2019-10-21
 */
@lombok.Data
@lombok.ToString(callSuper = true)
public class DglueAppQuery extends PagingQuery implements Serializable {

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
     * 注册地址
     */
    private String address;

    /**
     * 注册类型 1 自动注册 2 手动注册
     */
    private Integer registryType;

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