package com.ars.dglue.client.core;

import com.alibaba.fastjson.JSONObject;
import com.ars.dglue.client.core.model.GlueMessage;
import com.ars.dglue.client.zk.AbstractZkBroadcastWatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * 〈一句话介绍功能〉<br>
 * glue broadcaster
 *
 * @author jierui on 2019-10-08.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Slf4j
public class GlueBroadcaster extends AbstractZkBroadcastWatcher {

    /**
     * glue base root
     */
    private static final String GLUE_BASE_ROOT = "/dglue";

    /**
     * topic path: /dglue/[appCode]/[ruleCode]
     */
    private final String GLUE_APP_PATH;

    /**
     * glue broadcaster Single case
     */
    private static GlueBroadcaster instance = null;

    public static GlueBroadcaster getInstance() {
        return instance;
    }

    /**
     * zookeeper server constructor method
     *
     * @param zkServer
     * @param appCode
     */
    public GlueBroadcaster(String zkServer, String appCode) {
        super(zkServer);
        if (StringUtils.hasText(appCode)) {
            GLUE_APP_PATH = GLUE_BASE_ROOT + "/" + appCode;
        } else {
            GLUE_APP_PATH = GLUE_BASE_ROOT;
        }
        instance = this;
    }

    /**
     * consume message
     *
     * @param messageData message data
     * @return boolean
     * @throws Exception
     */
    @Override
    public boolean consumeMessage(String messageData) throws Exception {
        GlueMessage glueMessage = JSONObject.parseObject(messageData, GlueMessage.class);
        return false;
    }

    /**
     * watch message
     *
     * @param ruleCode rule code
     * @return boolean
     */
    public boolean watchMessage(String ruleCode) {
        String topic = GLUE_APP_PATH + "/" + ruleCode;
        return watchTopic(topic);
    }

    /**
     * produce message
     *
     * @param appCode  app code
     * @param ruleCode rule code
     * @param version  version
     * @return boolean
     */
    public boolean produceMessage(String appCode, String ruleCode, Integer version) {
        String topicPath = GLUE_BASE_ROOT + "/" + appCode + "/" + ruleCode;
        GlueMessage message = new GlueMessage();
        message.setRuleCode(ruleCode);
        message.setAppCode(appCode);
        message.setVersion(version);
        return super.produce(topicPath, JSONObject.toJSONString(message));
    }
}
