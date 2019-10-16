package com.ars.dglue.admin.controller;

import com.ars.dglue.admin.service.DglueAppService;
import org.springframework.web.bind.annotation.*;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-09-29.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping("/app")
public class AppManagerController {

    private DglueAppService dglueAppService;

    public AppManagerController(DglueAppService dglueAppService) {
        this.dglueAppService = dglueAppService;
    }

    @GetMapping("/{appId}")
    @ResponseBody
    public Object app(@PathVariable("appId") Long appId) {
        return dglueAppService.getById(appId);
    }


}
