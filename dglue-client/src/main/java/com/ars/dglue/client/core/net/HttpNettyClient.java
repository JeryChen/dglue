package com.ars.dglue.client.core.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.URL;

/**
 * 〈一句话介绍功能〉<br>
 * http netty client
 *
 * @author jierui on 2019-10-08.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class HttpNettyClient {

    private final EventLoopGroup eventLoopGroup;

    private final Bootstrap bootstrap;

    ResultHandler resultHandler;

    public HttpNettyClient(EventLoopGroup eventLoopGroup, Bootstrap bootstrap) {
        this.eventLoopGroup = eventLoopGroup;
        this.bootstrap = bootstrap;
        this.resultHandler = new ResultHandler(eventLoopGroup);
    }

    public Channel start(String url) throws Exception {
        URL urlObj = new URL(url);
        String host = urlObj.getHost();
        int port = urlObj.getPort();
        if (port == -1 && urlObj.getProtocol().equals("http")) {
            port = 80;
        }
        if (port == -1 && urlObj.getProtocol().equals("https")) {
            port = 443;
        }
        bootstrap.group(eventLoopGroup).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        channel.pipeline()
                                .addLast(new StringDecoder())
                                .addLast(new StringEncoder())
                                .addLast(new HttpObjectAggregator(1024 * 1024))
                                .addLast(new HttpNettyClientInboundHandler(resultHandler));
                    }
                });
        return bootstrap.connect(host, port).sync().channel();
    }

    public void get(String url) throws Exception {
        Channel channel = start(url);
        URL urlObj  = new URL(url);
        String path = urlObj.getPath();
        if(path == "") {
            path = "/";
        }
        String requestLine = "GET "+ path +" HTTP/1.1\n";
        String header = "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9,zh-TW;q=0.8\n" +
                "Connection: keep-alive\n" +
                "Host: " + urlObj.getHost() + "\n" +
                "User-Agent: AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36\n\n";
        String body = "";
        channel.writeAndFlush(requestLine + header + body);
        String result = resultHandler.getResultWithBlocking();
        System.out.println("response:" + result);
        close();
    }

    public void close() {
        if (null != eventLoopGroup) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        HttpNettyClient client = new HttpNettyClient(new NioEventLoopGroup(), new Bootstrap());
        client.get("http://dglue.ars.com/app/1");
    }

}
