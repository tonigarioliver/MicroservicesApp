package com.antonigari.steam.service.event;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.springframework.web.socket.WebSocketSession;


@EqualsAndHashCode(callSuper = true)
@Value
public class SubscribeMeasureEvent extends WebSocketEvent {
    public SubscribeMeasureEvent(final Object source, final String topic, final WebSocketSession session) {
        super(source, topic, session);
    }
}



