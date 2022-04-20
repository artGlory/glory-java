package com.glory.gloryGatewayNetty.tcp;

import com.glory.gloryUtils.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class MessagePacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        log.info("decode" + buffer);
        try {
            if (buffer.readableBytes() > 0) {
                // 待处理的消息包
                byte[] bytesReady = new byte[buffer.readableBytes()];
                buffer.readBytes(bytesReady);
                log.info("decode" + ByteUtil.bytesToHex(bytesReady));
                //这之间可以进行报文的解析处理
                out.add(bytesReady);
            }
        } finally {

        }
    }


}