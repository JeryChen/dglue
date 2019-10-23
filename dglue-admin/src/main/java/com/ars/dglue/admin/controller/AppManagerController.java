package com.ars.dglue.admin.controller;

import com.ars.dglue.admin.model.DglueApp;
import com.ars.dglue.admin.process.AppProcess;
import com.ars.dglue.admin.query.DglueAppQuery;
import com.ars.dglue.admin.service.DglueAppService;
import com.ars.dglue.admin.utils.PagingQuery;
import com.ars.dglue.admin.utils.PlatResult;
import com.ars.dglue.admin.vo.AppVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
@Slf4j
public class AppManagerController {

    private DglueAppService dglueAppService;

    private AppProcess appProcess;

    public AppManagerController(DglueAppService dglueAppService, AppProcess appProcess) {
        this.dglueAppService = dglueAppService;
        this.appProcess = appProcess;
    }

    /**
     * common search app info
     *
     * @param appVo       params
     * @param pagingQuery limit paging params
     * @return result
     */
    @GetMapping("/list")
    public PlatResult<List<DglueApp>> list(AppVo appVo, PagingQuery pagingQuery) {
        try {
            PlatResult<List<DglueApp>> result = PlatResult.success(null);
            DglueAppQuery appQuery = new DglueAppQuery();
            BeanUtils.copyProperties(appVo, appQuery);
            int count = dglueAppService.countByCondition(appQuery);
            if (count > 0) {
                pagingQuery.doPage(count);
                BeanUtils.copyProperties(pagingQuery, appQuery);
                List<DglueApp> appList = dglueAppService.listByCondition(appQuery);
                result.setResultObject(appList);
            }
            result.setPagingQuery(pagingQuery);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("get all apps exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("get all apps exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * get all apps name
     *
     * @return result
     */
    @GetMapping("/apps")
    public PlatResult<List<String>> apps() {
        try {
            List<DglueApp> appList = dglueAppService.listByCondition(new DglueAppQuery());
            List<String> apps = null;
            if (CollectionUtils.isEmpty(appList)) {
                apps = new ArrayList<>();
            } else {
                apps = appList.stream().map(DglueApp::getAppCode).collect(Collectors.toList());
            }
            return PlatResult.success(apps);
        } catch (IllegalArgumentException e) {
            log.error("get all apps name exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("get all apps name exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * add new app
     *
     * @param appVo app params
     * @return result
     */
    @PostMapping("/add")
    public PlatResult add(AppVo appVo) {
        try {
            appProcess.add(appVo);
            return PlatResult.success(null);
        } catch (IllegalArgumentException e) {
            log.error("add new app exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("add new app exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * update app info
     *
     * @param appVo app params
     * @return result
     */
    @PutMapping("/update")
    public PlatResult update(AppVo appVo) {
        try {
            appProcess.update(appVo);
            return PlatResult.success(null);
        } catch (IllegalArgumentException e) {
            log.error("update app info exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("update app info exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * delete one app info
     *
     * @param id app pk id
     * @return result
     */
    @DeleteMapping("/delete/{id}")
    public PlatResult delete(@PathVariable("id") Long id) {
        try {
            appProcess.delete(id);
            return PlatResult.success(null);
        } catch (IllegalArgumentException e) {
            log.error("delete one app info exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("delete one app info exception", e);
            return PlatResult.fail("system error!");
        }
    }
}
