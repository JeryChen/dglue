package com.ars.dglue.admin.process.impl;

import com.ars.dglue.admin.enums.RegistryTypeEnum;
import com.ars.dglue.admin.model.DglueApp;
import com.ars.dglue.admin.process.AppProcess;
import com.ars.dglue.admin.query.DglueAppQuery;
import com.ars.dglue.admin.query.DglueRuleQuery;
import com.ars.dglue.admin.service.DglueAppService;
import com.ars.dglue.admin.service.DglueRuleService;
import com.ars.dglue.admin.utils.UserHolder;
import com.ars.dglue.admin.vo.AppVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-22.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Service
public class AppProcessImpl implements AppProcess {

    private DglueAppService dglueAppService;

    private DglueRuleService dglueRuleService;

    public AppProcessImpl(DglueAppService dglueAppService, DglueRuleService dglueRuleService) {
        this.dglueAppService = dglueAppService;
        this.dglueRuleService = dglueRuleService;
    }

    /**
     * add new app
     *
     * @param appVo app params
     */
    @Override
    public void add(AppVo appVo) {
        validParams(appVo);
        DglueAppQuery query = new DglueAppQuery();
        query.setAppCode(appVo.getAppCode());
        int count = dglueAppService.countByCondition(query);
        Assert.isTrue(count <= 0, "该应用配置已存在");
        DglueApp app = new DglueApp();
        BeanUtils.copyProperties(appVo, app);
        app.setCreator(UserHolder.getUser().getUsername());
        app.setAddress(StringUtils.hasText(appVo.getAddress()) ? appVo.getAddress() : "");
        DglueApp result = dglueAppService.save(app);
        Assert.notNull(result.getId(), "保存应用配置失败");
    }

    /**
     * update one app
     *
     * @param appVo app params
     */
    @Override
    public void update(AppVo appVo) {
        validParams(appVo);
        Assert.notNull(appVo.getId(), "待更新应用id不能为空");
        DglueApp dglueApp = dglueAppService.getById(appVo.getId());
        Assert.notNull(dglueApp, "对应应用配置不存在");
        DglueApp app = new DglueApp();
        BeanUtils.copyProperties(appVo, app);
        app.setAddress(RegistryTypeEnum.AUTO.getCode().equals(appVo.getRegistryType()) ? "" : appVo.getAddress());
        app.setModifier(UserHolder.getUser().getUsername());
        app.setModifyTime(new Date());
        int result = dglueAppService.updateById(app);
        Assert.isTrue(result == 1, "更新应用配置失败");
    }

    /**
     * delete one app
     *
     * @param id app id
     */
    @Override
    public void delete(Long id) {
        Assert.notNull(id, "appId不能为空");
        DglueApp app = dglueAppService.getById(id);
        Assert.notNull(app, "对应应用配置不存在");
        DglueRuleQuery query = new DglueRuleQuery();
        query.setAppCode(app.getAppCode());
        int count = dglueRuleService.countByCondition(query);
        Assert.isTrue(count <= 0, "应用下存在规则配置无法删除");
        int result = dglueAppService.delete(id);
        Assert.isTrue(result == 1, "删除应用配置失败");
    }

    /**
     * params valid
     *
     * @param appVo app info
     */
    private void validParams(AppVo appVo) {
        Assert.notNull(appVo, "应用配置信息不能为空");
        Assert.hasText(appVo.getAppCode(), "应用编码不能为空");
        Assert.hasText(appVo.getAppName(), "应用名称不能为空");
        Assert.notNull(appVo.getRegistryType(), "注册方式不能为空");
        Assert.notNull(appVo.getRegistryType(), "注册方式不能为空");
        Assert.isTrue(RegistryTypeEnum.isExist(appVo.getRegistryType()), "非法注册方式");
        if (RegistryTypeEnum.MANUAL.getCode().equals(appVo.getRegistryType())) {
            Assert.hasText(appVo.getAddress(), "注册地址不能为空");
        }
    }
}
