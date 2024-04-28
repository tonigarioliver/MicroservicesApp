package com.antonigari.RealTimeDataService.event.listener;

import com.antonigari.RealTimeDataService.event.RemoveMeasureSubscriptionEvent;
import com.antonigari.RealTimeDataService.service.Impl.MqttClientService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RemoveMeasureSubscriptionListener implements ApplicationListener<RemoveMeasureSubscriptionEvent> {
    private final MqttClientService mqttClientService;

    @Override
    public void onApplicationEvent(final RemoveMeasureSubscriptionEvent event) {
        this.mqttClientService.removeWebSocketClientTopic(event.getSession(), event.getMeasurement());
    }
}