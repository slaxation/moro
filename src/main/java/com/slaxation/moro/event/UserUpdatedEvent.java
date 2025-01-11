package com.slaxation.moro.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserUpdatedEvent extends ApplicationEvent {

    private final String username;

    public UserUpdatedEvent(Object source, String username) {
        super(source);
        this.username = username;
    }

}
