package com.antonigari.RealTimeDataService.service.utilities;

import com.antonigari.RealTimeDataService.config.websocket.WebSocketCommands;
import com.antonigari.RealTimeDataService.event.NewMeasureSubscriptionEvent;
import com.antonigari.RealTimeDataService.event.RemoveMeasureSubscriptionEvent;
import com.antonigari.RealTimeDataService.model.DeviceMeasurementWebSocketRequest;
import com.antonigari.RealTimeDataService.model.WebSocketHandlerMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private ApplicationEventPublisher eventPublisher;
    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        this.webSocketSessions.remove(session);
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) throws Exception {

        super.handleMessage(session, message);
        final byte[] byteArrayPayload = ((TextMessage) message).asBytes();
        final String receivedJson = new String(byteArrayPayload, StandardCharsets.UTF_8);
        System.out.println("Received JSON: " + receivedJson);
        final WebSocketHandlerMessage webSocketHandlerMessage = this.objectMapper.readValue(receivedJson, WebSocketHandlerMessage.class);
        this.handleEventMessage(webSocketHandlerMessage, session);
        for (final WebSocketSession webSocketSession : this.webSocketSessions) {
            webSocketSession.sendMessage(message);
        }
    }


    private void handleEventMessage(final WebSocketHandlerMessage webSocketHandlerMessage,
                                    final WebSocketSession session) throws JsonProcessingException {
        final DeviceMeasurementWebSocketRequest deviceMeasurementWebSocketRequest = this.objectMapper.readValue(webSocketHandlerMessage.payload(), DeviceMeasurementWebSocketRequest.class);
        switch (WebSocketCommands.valueOf(webSocketHandlerMessage.command())) {
            case SUBSCRIBE:
                this.eventPublisher.publishEvent(new NewMeasureSubscriptionEvent(this, deviceMeasurementWebSocketRequest.topic(), session));
                break;
            case REMOVE:
                this.eventPublisher.publishEvent(new RemoveMeasureSubscriptionEvent(this, deviceMeasurementWebSocketRequest.topic(), session));
                break;
            default:
                break;
        }
    }

}