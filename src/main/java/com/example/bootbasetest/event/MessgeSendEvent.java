package com.example.bootbasetest.event;

import com.example.bootbasetest.dto.StringDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

@Getter
public class MessgeSendEvent extends ApplicationEvent {
    private final StringDto message;
    public MessgeSendEvent(Object source, StringDto message) {
        super(source);
        this.message = message;
    }
}
