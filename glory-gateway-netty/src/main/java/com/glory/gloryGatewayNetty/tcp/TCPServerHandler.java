package com.glory.gloryGatewayNetty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TCPServerHandler extends ChannelInboundHandlerAdapter {
    public TCPServerHandler() {
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            ByteBuf in = (ByteBuf) msg;
            int readableBytes = in.readableBytes();
            byte[] bytes = new byte[readableBytes];
            in.readBytes(bytes);
            System.out.println(new String(bytes));
            //System.out.print(in.toString(CharsetUtil.UTF_8));

            log.error("服务端接受的消息 : " + msg);
        } finally {
            // 抛弃收到的数据
            ReferenceCountUtil.release(msg);
        }

    }
}