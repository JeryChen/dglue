package com.ars.dglue.admin.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话介绍功能〉<br>
 * platform unified processing results
 *
 * @author jierui on 2019-10-18.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Data
public class PlatResult<T> implements Serializable {

    /**
     * success
     */
    public static final Integer SUCCESS = 1;

    /**
     * fail
     */
    public static final Integer FAILURE = 0;

    private static final long serialVersionUID = -1726639627958648721L;

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 返回业务对象
     */
    private T resultObject;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 异常堆栈信息
     */
    private String errorStack;

    /**
     * 分页
     */
    private PagingQuery pagingQuery;

    public PlatResult() {
    }

    public PlatResult(Integer code) {
        this.code = code;
    }

    public PlatResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public PlatResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.resultObject = data;
    }

    public static <T> PlatResult<T> success(T data) {
        return new PlatResult<>(SUCCESS, "处理成功", data);
    }

    public static <T> PlatResult<T> fail(String message) {
        return new PlatResult(FAILURE, message);
    }

}
