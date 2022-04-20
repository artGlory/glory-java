package com.glory.gloryGatewayNetty.util;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

import static io.netty.handler.codec.http.HttpUtil.is100ContinueExpected;

/**
 * @Description http请求工具类
 * @Author hyy
 * @Date 2022-04-06 8:13
 **/
public class NettyHttpUtil {
    public static void main(String[] args) {
        try {
            createHttpServer(6666);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void createHttpServer(int port) throws InterruptedException {
        EventLoopGroup boss=new NioEventLoopGroup();
        EventLoopGroup work=new NioEventLoopGroup();
        ServerBootstrap serverBootstrap=new ServerBootstrap();
        serverBootstrap.group(boss,work)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline channelPipeline=ch.pipeline();
                        channelPipeline.addLast(new HttpServerCodec());
                        // http 消息聚合器
                        // 512*1024为接收的最大contentlength
                        channelPipeline.addLast("httpAggregator",new HttpObjectAggregator(512*1024));
                        channelPipeline.addLast(new SimpleChannelInboundHandler<FullHttpRequest>(){

                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
                                //100 Continue
                                if (is100ContinueExpected(msg)) {
                                    ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE));
                                }

                                System.err.println(msg);
                                System.err.println(msg.content().toString(CharsetUtil.UTF_8));
                                // 创建http响应
                                String responseStr="返回信息";
                                    FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK
                                        , Unpooled.copiedBuffer((CharSequence) responseStr, CharsetUtil.UTF_8));
                                // 设置头信息
                                response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
                                //response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
                                // 将html write到客户端
                                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                            }
                        });// 请求处理器
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(port)).sync();
        channelFuture.channel().closeFuture().sync();
    }


}
