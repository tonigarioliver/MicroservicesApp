package com.antonigari.RealTimeDataService.event;

import lombok.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.socket.WebSocketSession;

@Value
public class NewMeasureSubscriptionEvent extends ApplicationEvent {
    String topic;
    WebSocketSession session;

    public NewMeasureSubscriptionEvent(final Object source, final String topic, final WebSocketSession session) {
        super(source);
        this.topic = topic;
        this.session = session;
    }
}
