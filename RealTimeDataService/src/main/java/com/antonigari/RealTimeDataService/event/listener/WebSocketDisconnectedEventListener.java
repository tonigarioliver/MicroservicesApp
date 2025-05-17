package com.antonigari.RealTimeDataService.event.listener;


import com.antonigari.RealTimeDataService.event.WebSocketDisconnectEvent;
import com.antonigari.RealTimeDataService.service.Impl.MqttClientService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class WebSocketDisconnectedEventListener implements ApplicationListener<WebSocketDisconnectEvent> {
    private final MqttClientService mqttClientService;

    @Override
    public void onApplicationEvent(final WebSocketDisconnectEvent event) {
        this.mqttClientService.webSocketClientListenerDisconnected(event.getSession());
    }
}