package com.ars.dglue.client.core;

import com.ars.dglue.client.core.model.GlueMessage;
import com.ars.dglue.client.loader.IGlueLoader;
import groovy.lang.GroovyClassLoader;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 〈一句话介绍功能〉<br>
 * glue execute factory
 *
 * @author jierui on 2019-10-08.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Slf4j
public class GlueFactory implements ApplicationContextAware {

    /**
     * glue handler instance cache
     * key:ruleCode, value:handler instance
     */
    private static final ConcurrentHashMap<String, IGlueHandler> glueHandlerMap = new ConcurrentHashMap<>(128);

    /**
     * glue version cache
     * key:ruleCode, value:version
     */
    private static final ConcurrentHashMap<String, Integer> glueVersionMap = new ConcurrentHashMap<>(128);

    /**
     * async refresh queue, validate version: avoid caching avalanches
     */
    private static final LinkedBlockingQueue<GlueMessage> glueRefreshQueue = new LinkedBlockingQueue<>();

    /**
     * app code
     */
    @Setter
    @NonNull
    private String appCode;

    /**
     * zk registry address
     */
    @Setter
    @NonNull
    private String zookeeperAddress;

    /**
     * groovy class loader
     */
    private GroovyClassLoader groovyClassLoader = new GroovyClassLoader();

    /**
     * spring application context
     */
    private ApplicationContext applicationContext;

    /**
     * glue execute factory
     */
    private static GlueFactory glueFactory;

    /**
     * glue source code loader
     */
    private IGlueLoader glueLoader;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        glueFactory = this.applicationContext.getBean(GlueFactory.class);
    }

    /**
     * init
     */
    private void init() {

    }

    /**
     * refresh glue method
     *
     * @param glueMessage glue message
     * @return boolean
     */
    public static boolean refresh(GlueMessage glueMessage) {
        try {
            if (glueFactory.appCode.equals(glueMessage.getAppCode())) {
                glueRefreshQueue.add(glueMessage);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
