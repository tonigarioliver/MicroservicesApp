package com.antonigari.steam.service.event.listener;

import com.antonigari.steam.service.event.UnsubscribeMeasureEvent;
import com.antonigari.steam.service.service.Impl.MqttClientService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemoveMeasureSubscriptionListener implements ApplicationListener<UnsubscribeMeasureEvent> {
    private final MqttClientService mqttClientService;

    @Override
    public void onApplicationEvent(final UnsubscribeMeasureEvent event) {
        this.mqttClientService.removeWebSocketClientTopic(event.getSession(), event.getTopic());
    }
}