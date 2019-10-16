package com.ars.dglue.client.core.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话介绍功能〉<br>
 * glue message info
 *
 * @author jierui on 2019-10-08.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class GlueMessage implements Serializable {

    /**
     * rule code
     */
    private String ruleCode;

    /**
     * app code
     */
    private String appCode;

    /**
     * version
     */
    private Integer version;
}
