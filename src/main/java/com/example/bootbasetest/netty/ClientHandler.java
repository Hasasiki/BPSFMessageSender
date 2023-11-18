package com.example.bootbasetest.netty;

import com.example.bootbasetest.conf.ChannelManager;
import com.example.bootbasetest.event.MessgeSendEvent;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class ClientHandler  extends SimpleChannelInboundHandler<String> {
    private final ChannelManager channelManager;
    ChannelHandlerContext ctx;

    public ClientHandler(ChannelManager channelManager) {
        this.channelManager = channelManager;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 当连接建立时被调用
        Channel channel = ctx.channel();
        this.ctx = ctx;
        channelManager.addChannel("bpbp", ctx);
        log.info("Client connected: " + channel.remoteAddress());
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 当连接断开时被调用
        Channel channel = ctx.channel();
        String clientId = "someUniqueClientId"; // 这里需要根据具体的业务逻辑来获取客户端标识符
        log.info("Client disconnected: " + channel.remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 异常处理
        cause.printStackTrace();
        ctx.close();
    }

    // 添加一个方法用于发送消息
    public void sendMessage(String message) {
        byte[] asciiBytes = message.getBytes(StandardCharsets.US_ASCII);
        ChannelHandlerContext ctxx = channelManager.getChannel("bpbp");
        ctxx.writeAndFlush(Unpooled.copiedBuffer(asciiBytes));
        log.info("BP Mesage Sended");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        log.info("BP Mesage Received: " + s);
    }
    @EventListener
    public void onMessgeSendEven(MessgeSendEvent event) {
        sendMessage(event.getMessage().getString());
    }
}
