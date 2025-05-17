package com.antonigari.RealTimeDataService.event.listener;

import com.antonigari.RealTimeDataService.event.SubscribeMeasureEvent;
import com.antonigari.RealTimeDataService.service.Impl.MqttClientService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NewMeasureSubscriptionListener implements ApplicationListener<SubscribeMeasureEvent> {
    private final MqttClientService mqttClientService;

    @Override
    public void onApplicationEvent(final SubscribeMeasureEvent event) {
        this.mqttClientService.addWebSocketClientTopic(event.getSession(), event.getTopic());
    }
}