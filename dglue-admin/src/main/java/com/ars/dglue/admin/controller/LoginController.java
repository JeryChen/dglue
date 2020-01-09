package com.ars.dglue.admin.controller;

import com.ars.dglue.admin.utils.PlatResult;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2020-01-08.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class LoginController {

    @GetMapping("/login")
    public PlatResult login(@RequestParam String username, @RequestParam String password, HttpServletRequest request,
                            HttpServletResponse response) {
        PlatResult result = new PlatResult();
        return result;

    }
}
