package com.example.bootbasetest.netty;

import com.example.bootbasetest.conf.ChannelManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.stereotype.Component;

@Component
public class BPNettyTCPClient {
    private final ChannelManager c;

    public BPNettyTCPClient(ChannelManager c) {
        this.c = c;
    }

    public void run(String host, int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new BPDecoder()).addLast(new ClientHandler(c)).addLast(new LoggingHandler()); // 替换成你自己实现的处理器
                        }
                    });

            // 连接到服务器
            Channel channel = bootstrap.connect(host, port).sync().channel();

            // 在这里可以发送消息给服务器
            // channel.writeAndFlush(...);

            // 等待直到连接关闭
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

}
