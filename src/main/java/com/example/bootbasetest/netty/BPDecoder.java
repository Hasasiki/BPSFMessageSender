package com.example.bootbasetest.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class BPDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 检查是否有足够的字节可以被读取
        if (in.readableBytes() > 0) {
            // 读取数据并转换为字符串
            byte[] bytes = new byte[in.readableBytes()];
            in.readBytes(bytes);
            String message = new String(bytes, StandardCharsets.US_ASCII);
            out.add(message);
        }
    }
}
