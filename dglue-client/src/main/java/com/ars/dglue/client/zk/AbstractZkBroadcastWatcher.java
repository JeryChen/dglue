package com.ars.dglue.client.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;

/**
 * 〈一句话介绍功能〉<br>
 * zookeeper broad cast watcher
 *
 * @author jierui on 2019-10-08.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Slf4j
public abstract class AbstractZkBroadcastWatcher extends ZkClient {


    /**
     * zookeeper server constructor method
     *
     * @param zkServer
     */
    public AbstractZkBroadcastWatcher(String zkServer) {
        super(zkServer);
    }

    /**
     * process watched event
     *
     * @param watchedEvent event
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.Expired) {
            super.close();
            super.getZooKeeper();
        }
        if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            String path = watchedEvent.getPath();

            /**
             * add one-time watch
             *
             * It is import, Because we need the latest data, Not real time, one-time can guarantee the Watch event,
             * will not repeat the monitoring response during the re-Watch period, But it can guarantee that
             * get Data is the latest, and will check if there is new data in the middle through temporary check.
             *
             */
            try {
                super.getZooKeeper().exists(path, true);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }

            String data = null;
            try {
                byte[] resultData = super.getZooKeeper().getData(path, true, null);
                data = new String(resultData);
            } catch (KeeperException e) {
                log.error(e.getMessage(), e);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            consume(path, data);
        }
    }

    /**
     * message data producer
     *
     * @param nodePath    node path
     * @param messageData message data
     * @return boolean
     */
    protected boolean produce(String nodePath, String messageData) {
        Stat stat = setData(nodePath, messageData);
        boolean isSuccess = null != stat ? true : false;
        log.info("AbstractZkBroadcastWatcher.produce message: stat={}, nodePath={}, messageData={}", isSuccess,
                nodePath, messageData);
        return isSuccess;
    }

    /**
     * consume message (watch node)
     *
     * @param nodePath    node path
     * @param messageData message data
     */
    protected void consume(String nodePath, String messageData) {
        try {
            boolean isSuccess = consumeMessage(messageData);
            log.info("AbstractZkBroadcastWatcher.consume message: stat={}, nodePath{}, messageData={}", isSuccess,
                    nodePath, messageData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * watch broadcast topic (watch node)
     *
     * @param path node path
     * @return boolean
     */
    protected boolean watchTopic(String path) {
        try {
            Stat stat = super.getZooKeeper().exists(path, true);
            if (stat == null) {
                stat = super.isExistOrCreate(path);
                stat = super.getZooKeeper().exists(path, true);
            }
            boolean isSuccess = null != stat ? true : false;
            log.info("AbstractZkBroadcastWatcher.watchTopic:{}, path:{}", isSuccess, path);
            return isSuccess;
        } catch (KeeperException e) {
            log.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    /**
     * consume message
     *
     * @param messageData message data
     * @return boolean
     * @throws Exception
     */
    public abstract boolean consumeMessage(String messageData) throws Exception;
}
