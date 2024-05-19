package com.antonigari.RealTimeDataService.event;

import lombok.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

@Value
public class WebSocketDisconnectedEvent extends ApplicationEvent {
    WebSocketSession session;

    public WebSocketDisconnectedEvent(final Object source, final WebSocketSession session) {
        super(source);
        this.session = session;
    }
}
