package com.ars.dglue.admin.vo;

import lombok.Data;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-18.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class AppVo {

    private Long id;

    private String appCode;

    private String appName;

    private Integer registryType;

    private String address;
}
