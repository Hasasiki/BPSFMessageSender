package com.example.bootbasetest.conf;

import com.example.bootbasetest.netty.BPNettyTCPClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Slf4j
@Configuration
public class TcpClientAutoConfiguration {
    private final BPNettyTCPClient client;

    public TcpClientAutoConfiguration(BPNettyTCPClient client) {
        this.client = client;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        try {
            this.client.run("172.16.23.191",2101);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
