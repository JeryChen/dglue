package com.ars.dglue.admin.controller.interceptor;

import com.ars.dglue.admin.dto.UserDTO;
import com.ars.dglue.admin.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-18.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">>>我是一个拦截器");
        UserDTO dto = new UserDTO();
        dto.setId(1);
        dto.setUsername("杰瑞");
        UserHolder.setUser(dto);
        return true;
    }
}
