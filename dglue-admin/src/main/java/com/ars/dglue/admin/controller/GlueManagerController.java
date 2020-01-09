package com.ars.dglue.admin.controller;

import com.ars.dglue.admin.model.DglueRule;
import com.ars.dglue.admin.process.RuleProcess;
import com.ars.dglue.admin.query.DglueRuleQuery;
import com.ars.dglue.admin.service.DglueRuleService;
import com.ars.dglue.admin.utils.PagingQuery;
import com.ars.dglue.admin.utils.PlatResult;
import com.ars.dglue.admin.vo.RuleVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-23.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController
@RequestMapping("/glue")
@Slf4j
@AllArgsConstructor
public class GlueManagerController {

    private DglueRuleService dglueRuleService;

    private RuleProcess ruleProcess;

    /**
     * common search rule info
     *
     * @param ruleVo      rule params
     * @param pagingQuery limit paging params
     * @return result
     */
    @GetMapping("/list")
    public PlatResult<List<DglueRule>> list(RuleVo ruleVo, PagingQuery pagingQuery) {
        try {
            PlatResult<List<DglueRule>> result = PlatResult.success(null);
            DglueRuleQuery ruleQuery = new DglueRuleQuery();
            BeanUtils.copyProperties(ruleVo, ruleQuery);
            int count = dglueRuleService.countByCondition(ruleQuery);
            if (count > 0) {
                pagingQuery.doPage(count);
                BeanUtils.copyProperties(pagingQuery, ruleQuery);
                List<DglueRule> appList = dglueRuleService.listByCondition(ruleQuery);
                result.setResultObject(appList);
            }
            result.setPagingQuery(pagingQuery);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("common search rule info exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("common search rule info exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * add new rule
     *
     * @param ruleVo rule params
     * @return result
     */
    @PostMapping("/add")
    public PlatResult add(RuleVo ruleVo) {
        try {
            ruleProcess.add(ruleVo);
            return PlatResult.success(null);
        } catch (IllegalArgumentException e) {
            log.error("add new rule info exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("add new rule info exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * update one rule info
     *
     * @param ruleVo rule params
     * @return result
     */
    @PutMapping("/add")
    public PlatResult update(RuleVo ruleVo) {
        try {
            ruleProcess.update(ruleVo);
            return PlatResult.success(null);
        } catch (IllegalArgumentException e) {
            log.error("update rule info exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("update rule info exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * delete one rule info
     *
     * @param id rule id
     * @return result
     */
    @DeleteMapping("/delete/{id}")
    public PlatResult delete(@PathVariable("id") Long id) {
        try {
            ruleProcess.delete(id);
            return PlatResult.success(null);
        } catch (IllegalArgumentException e) {
            log.error("delete one rule info exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("delete one rule info exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * test one rule code
     *
     * @param source code
     * @return result
     */
    @PostMapping("/test")
    public PlatResult test(String source) {
        try {
            ruleProcess.test(source);
            return PlatResult.success(null);
        } catch (IllegalArgumentException e) {
            log.error("test one rule exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("test one rule exception", e);
            return PlatResult.fail("system error!");
        }
    }

    /**
     * publish one rule
     *
     * @param id rule id
     * @return result
     */
    @PostMapping("/publish/{id}")
    public PlatResult publish(@PathVariable("id") Long id) {
        try {
            ruleProcess.publish(id);
            return PlatResult.success(null);
        } catch (IllegalArgumentException e) {
            log.error("publish one rule exception", e);
            return PlatResult.fail(e.getMessage());
        } catch (Exception e) {
            log.error("publish one rule exception", e);
            return PlatResult.fail("system error!");
        }
    }

}
