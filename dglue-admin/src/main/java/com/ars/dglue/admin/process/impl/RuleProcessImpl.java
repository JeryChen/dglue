package com.ars.dglue.admin.process.impl;

import com.ars.dglue.admin.enums.RuleTypeEnum;
import com.ars.dglue.admin.model.DglueApp;
import com.ars.dglue.admin.model.DglueRule;
import com.ars.dglue.admin.process.RuleProcess;
import com.ars.dglue.admin.query.DglueAppQuery;
import com.ars.dglue.admin.query.DglueRuleQuery;
import com.ars.dglue.admin.service.DglueAppService;
import com.ars.dglue.admin.service.DglueRuleService;
import com.ars.dglue.admin.utils.JavaSyntaxChecker;
import com.ars.dglue.admin.utils.UserHolder;
import com.ars.dglue.admin.vo.RuleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-24.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
@Slf4j
public class RuleProcessImpl implements RuleProcess {

    private DglueRuleService dglueRuleService;

    private DglueAppService dglueAppService;

    public RuleProcessImpl(DglueRuleService dglueRuleService) {
        this.dglueRuleService = dglueRuleService;
    }

    /**
     * add new rule
     *
     * @param ruleVo rule params
     */
    @Override
    public void add(RuleVo ruleVo) {
        validParams(ruleVo);
        DglueRuleQuery ruleQuery = new DglueRuleQuery();
        ruleQuery.setRuleCode(ruleVo.getRuleCode());
        int count = dglueRuleService.countByCondition(ruleQuery);
        Assert.isTrue(count <= 0, "该规则配置已存在");
        DglueAppQuery appQuery = new DglueAppQuery();
        appQuery.setAppCode(ruleVo.getAppCode());
        List<DglueApp> appList = dglueAppService.listByCondition(appQuery);
        Assert.notEmpty(appList, "非法应用编码");
        DglueRule rule = new DglueRule();
        BeanUtils.copyProperties(ruleVo, rule);
        rule.setCreator(UserHolder.getUser().getUsername());
        rule.setAppName(appList.get(1).getAppName());
        rule.setVersion(1);
        DglueRule result = dglueRuleService.save(rule);
        Assert.notNull(result.getId(), "保存规则信息失败");
    }

    /**
     * update one rule info
     *
     * @param ruleVo rule params
     */
    @Override
    public void update(RuleVo ruleVo) {

    }

    /**
     * delete one rule info
     *
     * @param ruleId rule id
     */
    @Override
    public void delete(Long ruleId) {

    }

    /**
     * test java code is not true
     *
     * @param source code
     */
    @Override
    public void test(String source) {
        Assert.hasText(source, "待检测代码不能为空");
        File file = null;
        OutputStream os = null;
        try {
            String className = source.substring(source.indexOf("class") + 5, source.indexOf("{")).trim();
            file = new File("/Users/jierui/Downloads/" + className.replaceAll("", "") + ".java");
            os = new FileOutputStream(file);
            os.write(source.getBytes("UTF-8"));
            os.flush();
            List<String> checkResultList = JavaSyntaxChecker.check(file.getPath());
            Assert.isTrue(CollectionUtils.isEmpty(checkResultList), "代码语法错误");
        } catch (Throwable e) {
            log.error("检测代码语法异常", e);
        } finally {
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != file) {
                file.delete();
            }
        }
    }

    /**
     * publish rule
     *
     * @param id rule id
     */
    @Override
    public void publish(Long id) {

    }

    /**
     * valid rule params
     *
     * @param ruleVo rule params
     */
    private void validParams(RuleVo ruleVo) {
        Assert.notNull(ruleVo, "规则对象不能为空");
        Assert.hasText(ruleVo.getAppCode(), "应用编码不能为空");
        Assert.hasText(ruleVo.getRuleCode(), "规则编码不能为空");
        Assert.hasText(ruleVo.getRuleName(), "规则名称不能为空");
        Assert.notNull(ruleVo.getRuleType(), "规则类型不能为空");
        Assert.isTrue(RuleTypeEnum.isExist(ruleVo.getRuleType()), "非法规则类型");
        Assert.hasText(ruleVo.getSource(), "规则源码不能为空");
    }
}
