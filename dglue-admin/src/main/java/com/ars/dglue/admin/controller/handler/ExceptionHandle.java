package com.ars.dglue.admin.controller.handler;

import com.ars.commons.exception.BusinessException;
import com.ars.dglue.admin.utils.PlatResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2020-01-07.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(IllegalArgumentException.class)
    public PlatResult handleArgumentException(IllegalArgumentException e) {
        return PlatResult.fail(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public PlatResult handleBusinessException(BusinessException e) {
        return PlatResult.fail(e.getMessage());
    }
}
