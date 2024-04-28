package com.antonigari.RealTimeDataService.event.listener;

import com.antonigari.RealTimeDataService.event.NewMeasureSubscriptionEvent;
import com.antonigari.RealTimeDataService.service.Impl.MqttClientService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class NewMeasureSubscriptionListener implements ApplicationListener<NewMeasureSubscriptionEvent> {
    private final MqttClientService mqttClientService;

    @Override
    public void onApplicationEvent(final NewMeasureSubscriptionEvent event) {
        this.mqttClientService.addWebSocketClient(event.getSession(), event.getTopic());
    }
}