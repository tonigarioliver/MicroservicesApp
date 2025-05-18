package com.antonigari.steam.service.event.listener;


import com.antonigari.steam.service.event.WebSocketDisconnectEvent;
import com.antonigari.steam.service.service.Impl.MqttClientService;
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