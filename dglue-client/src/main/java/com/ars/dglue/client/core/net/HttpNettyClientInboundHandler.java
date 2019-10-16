package com.ars.dglue.client.core.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话介绍功能〉<br>
 * http netty client inbound handler
 *
 * @author jierui on 2019-10-09.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Slf4j
public class HttpNettyClientInboundHandler extends SimpleChannelInboundHandler {

    private final ResultHandler resultHandler;

    public HttpNettyClientInboundHandler(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        resultHandler.setResult(o.toString());
    }
}
