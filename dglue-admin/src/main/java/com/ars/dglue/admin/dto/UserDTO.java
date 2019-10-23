package com.ars.dglue.admin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话介绍功能〉<br>
 * user info
 *
 * @author jierui on 2019-10-18.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 875930271124944124L;

    /**
     * user id
     */
    private Integer id;

    /**
     * user name
     */
    private String username;

}
