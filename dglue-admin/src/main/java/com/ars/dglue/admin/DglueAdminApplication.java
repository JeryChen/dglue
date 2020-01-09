package com.ars.dglue.admin;

import com.ars.commons.interceptor.LoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({LoginInterceptor.class})
public class DglueAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DglueAdminApplication.class, args);
    }

}
