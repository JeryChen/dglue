package com.ars.dglue.client.core.net;

import io.netty.channel.EventLoopGroup;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 〈一句话介绍功能〉<br>
 * 〈功能详细描述〉
 *
 * @author jierui on 2019-10-09.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class ResultHandler {

    private EventLoopGroup group;

    private AtomicReference<String> result = new AtomicReference<>();

    public ResultHandler() {

    }

    public ResultHandler(EventLoopGroup group) {
        this.group = group;
    }

    public String getResultWithoutBlocking() {
        if(null != result && null != group) {
            group.shutdownGracefully();
        }
        return result.get();
    }

    public String getResultWithBlocking() {
        while(null == result.get()) {
            //循环等待
        }
        if(null != group) {
            group.shutdownGracefully();
        }
        return result.get();
    }

    public String getResultWithBlocking(long timeout) {
        long time = System.currentTimeMillis();
        while(null == result.get()) {
            if(System.currentTimeMillis() - time > timeout) {
                break;
            }
        }
        return result.get();
    }

    public void setResult(String resultStr) {
        result.set(resultStr);
    }

    public void setGroup(EventLoopGroup group) {
        this.group = group;
    }

}
