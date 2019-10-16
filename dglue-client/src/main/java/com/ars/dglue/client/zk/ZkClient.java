package com.ars.dglue.client.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 〈一句话介绍功能〉<br>
 * <p>
 * zkClient init and listen to event change
 *
 * @author jierui on 2019-09-30.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Slf4j
public abstract class ZkClient implements Watcher {

    /**
     * zookeeper server name space
     */
    private String zkServer;

    /**
     * zookeeper
     */
    private ZooKeeper zooKeeper;

    /**
     * zookeeper server constructor method
     *
     * @param zkServer
     */
    public ZkClient(String zkServer) {
        this.zkServer = zkServer;
    }

    /**
     * reentrantLock
     */
    private ReentrantLock reentrantLock = new ReentrantLock(true);

    /**
     * get a zookeeper connection, if connection is dest not exist, create a new zookeeper connection.
     *
     * @return zookeeper entry
     */
    protected ZooKeeper getZooKeeper() {
        if (null != zooKeeper) {
            return zooKeeper;
        }
        try {
            if (reentrantLock.tryLock(2, TimeUnit.SECONDS)) {
                if (null != zooKeeper) {
                    return zooKeeper;
                }
                zooKeeper = new ZooKeeper(zkServer, 1000, this);
                log.info("ZkClient.zookeeper connect success.");
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            reentrantLock.unlock();
        }
        return zooKeeper;
    }

    /**
     * close zookeeper connection
     */
    protected void close() {
        if (null != zooKeeper) {
            try {
                zooKeeper.close();
                zooKeeper = null;
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    /**
     * node path is exist, if not, create new node path
     *
     * @param nodePath node path
     * @return stat
     * @throws KeeperException
     * @throws InterruptedException
     */
    protected Stat isExistOrCreate(String nodePath) throws KeeperException, InterruptedException {
        if (StringUtils.hasText(nodePath)) {
            return null;
        }
        Stat stat = zooKeeper.exists(nodePath, false);
        if (stat != null) {
            return stat;
        } else {
            String parentPath = nodePath.substring(0, nodePath.lastIndexOf("/"));
            if (parentPath.length() < nodePath.length()) {
                isExistOrCreate(parentPath);
            }
            zooKeeper.create(nodePath, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return null;
        }
    }

    /**
     * set node path data
     *
     * @param nodePath node path
     * @param data     data
     * @return stat
     */
    protected Stat setData(String nodePath, String data) {
        try {
            Stat stat = isExistOrCreate(nodePath);
            return getZooKeeper().setData(nodePath, data.getBytes(), null != stat ? stat.getVersion() : -1);
        } catch (KeeperException e) {
            log.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
