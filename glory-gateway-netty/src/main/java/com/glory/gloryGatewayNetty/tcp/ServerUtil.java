package com.glory.gloryGatewayNetty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * @Description http请求工具类
 * @Author hyy
 * @Date 2022-04-06 8:13
 **/
@Slf4j
public class ServerUtil {

    // 名字
    private String serverName;
    // 服务类型
    private String serverType = "TCP服务";
    //服务器运行状态
    private volatile boolean isRunning = false;
    // 端口
    private int serverPort;
    //处理Accept连接事件的线程，这里线程数设置为1即可，netty处理链接事件默认为单线程，过度设置反而浪费cpu资源
    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    //处理hadnler的工作线程，其实也就是处理IO读写 。线程数据默认为 CPU 核心数乘以2
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    public static void main(String[] args) {
        ServerUtil serverUtil=new ServerUtil("sdf");
        try {
            serverUtil.runServer(6666);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ServerUtil(String serverName) {
        this.serverName = serverName;
    }

    public synchronized void runServer(int port) throws Exception {
        this.serverPort = port;
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .channel(NioServerSocketChannel.class)
                    //标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 是否启用心跳保活机机制
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(10, 0, 0, TimeUnit.SECONDS));
//                            pipeline.addLast(new HeartBeatServerHandler());
                            // netty基于分割符的自带解码器，根据提供的分隔符解析报文，这里是0x7e;1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
//                            pipeline.addLast(new DelimiterBasedFrameDecoder(1024
//                                            , Unpooled.copiedBuffer(new byte[]{0x7e})
//                                            , Unpooled.copiedBuffer(new byte[]{0x7e})
//                                    )
//
//                            );
                            //自定义编解码器
                            pipeline.addLast(
                                    new MessagePacketDecoder(),
                                    new MessagePacketEncoder()
                            );
                            //自定义Hadler
                            pipeline.addLast("handler",new TCPServerHandler());
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(port)).sync();
            if (channelFuture.isSuccess()) {
                this.isRunning = true;
            }
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            this.isRunning = false;
            throw e;
        } finally {

        }
    }

    /**
     * @return void
     * @Description 停止服务
     * @Param []
     * @Author hyy
     * @Date 2022-04-07 9:26
     **/
    public synchronized void stopServer() {
        if (!this.isRunning) {
            throw new IllegalStateException(this.getServerName() + " 未启动 .");
        }
        try {
            this.isRunning = false;
            Future<?> future = this.workerGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                this.isRunning = true;
                log.error("workerGroup 无法正常停止:{}", future.cause());
            }

            future = this.bossGroup.shutdownGracefully().await();
            if (!future.isSuccess()) {
                this.isRunning = true;
                log.error("bossGroup 无法正常停止:{}", future.cause());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getServerName() {
        return serverName;
    }

    public int getServerPort() {
        return serverPort;
    }

    public String getServerType() {
        return serverType;
    }

    private String logPre() {
        String str = "[" + this.getServerType() + "]";
        str += "[serverName]" + this.getServerName();
        str += "[serverPort]" + this.getServerPort();
        return str;
    }
}
