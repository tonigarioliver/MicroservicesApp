package com.antonigari.RealTimeDataService.service.utilities;

import com.antonigari.RealTimeDataService.config.websocket.WebSocketCommands;
import com.antonigari.RealTimeDataService.event.NewMeasureSubscriptionEvent;
import com.antonigari.RealTimeDataService.event.RemoveMeasureSubscriptionEvent;
import com.antonigari.RealTimeDataService.event.WebSocketDisconnectedEvent;
import com.antonigari.RealTimeDataService.model.DeviceMeasurementWebSocketRequest;
import com.antonigari.RealTimeDataService.model.WebSocketHandlerMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class WebSocketHandler extends TextWebSocketHandler {
    private ApplicationEventPublisher eventPublisher;
    List<WebSocketSession> webSocketSessions;
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
        this.eventPublisher.publishEvent(new WebSocketDisconnectedEvent(this, session));
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        final String receivedJson = new String(((TextMessage) message).asBytes(), StandardCharsets.UTF_8);
        log.info("Received message: " + receivedJson);
        this.handleEventMessage(this.objectMapper.readValue(receivedJson, WebSocketHandlerMessage.class), session);
    }


    private void handleEventMessage(
            final WebSocketHandlerMessage webSocketHandlerMessage,
            final WebSocketSession session
    ) throws JsonProcessingException {
        final DeviceMeasurementWebSocketRequest deviceMeasurementWebSocketRequest = this.objectMapper.readValue(webSocketHandlerMessage.payload(), DeviceMeasurementWebSocketRequest.class);
        switch (WebSocketCommands.valueOf(webSocketHandlerMessage.command())) {
            case SUBSCRIBE-> this.eventPublisher.publishEvent(new NewMeasureSubscriptionEvent(this, deviceMeasurementWebSocketRequest.topic(), session));
            case REMOVE-> this.eventPublisher.publishEvent(new RemoveMeasureSubscriptionEvent(this, deviceMeasurementWebSocketRequest.topic(), session));
            default -> this.eventPublisher.publishEvent(new WebSocketDisconnectedEvent(this, session));
        }
    }

}