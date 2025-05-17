package com.antonigari.RealTimeDataService.event;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.web.socket.WebSocketSession;

@EqualsAndHashCode(callSuper = true)
@Value
public class WebSocketDisconnectEvent extends WebSocketEvent {
    public WebSocketDisconnectEvent(final Object source, final WebSocketSession session) {
        super(source, session);
    }
}