package com.antonigari.RealTimeDataService.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

@EqualsAndHashCode(callSuper = true)
@Getter
public abstract class WebSocketEvent extends ApplicationEvent {
    protected final transient WebSocketSession session;
    protected final String topic;

    protected WebSocketEvent(final Object source, final WebSocketSession session) {
        this(source, null, session);
    }

    protected WebSocketEvent(final Object source, final String topic, final WebSocketSession session) {
        super(source);
        this.session = session;
        this.topic = topic;
    }
}

