package com.example.bootbasetest.conf;

import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChannelManager {
    private final ConcurrentHashMap<String, ChannelHandlerContext> channels = new ConcurrentHashMap<>();

    public void addChannel(String id, ChannelHandlerContext ctx) {
        channels.put(id, ctx);
    }

    public void removeChannel(String id) {
        channels.remove(id);
    }

    public ChannelHandlerContext getChannel(String id) {
        return channels.get(id);
    }

    // 其他必要的方法，比如发送消息、关闭所有连接等
}
