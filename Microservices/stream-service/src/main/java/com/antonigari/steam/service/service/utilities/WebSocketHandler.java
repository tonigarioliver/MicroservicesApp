package com.antonigari.steam.service.service.utilities;

import com.antonigari.steam.service.config.websocket.WebSocketCommands;
import com.antonigari.steam.service.event.SubscribeMeasureEvent;
import com.antonigari.steam.service.event.UnsubscribeMeasureEvent;
import com.antonigari.steam.service.event.WebSocketDisconnectEvent;
import com.antonigari.steam.service.model.DeviceMeasurementWebSocketRequest;
import com.antonigari.steam.service.model.WebSocketHandlerMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@AllArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper = JsonMapper.builder()
            .addModule(new ParameterNamesModule())
            .addModule(new Jdk8Module())
            .addModule(new JavaTimeModule())
            .build();

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        log.info("WebSocket connection established for session: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        this.publishEvent(new WebSocketDisconnectEvent(this, session));
        log.info("WebSocket connection closed for session: {}, status: {}", session.getId(), status);
    }


    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);
        final var receivedJson = parseMessageContent((TextMessage) message);
        log.info("Received message from session {}: {}", session.getId(), receivedJson);

        final var handlerMessage = this.parseHandlerMessage(receivedJson);
        this.processWebSocketMessage(handlerMessage, session);
    }

    private static String parseMessageContent(final TextMessage message) {
        return new String(message.asBytes(), StandardCharsets.UTF_8);
    }

    private WebSocketHandlerMessage parseHandlerMessage(final String json) throws JsonProcessingException {
        return this.objectMapper.readValue(json, WebSocketHandlerMessage.class);
    }

    private void processWebSocketMessage(final WebSocketHandlerMessage handlerMessage, final WebSocketSession session)
            throws JsonProcessingException {
        final var request = this.objectMapper.readValue(handlerMessage.payload(),
                DeviceMeasurementWebSocketRequest.class);

        final var command = WebSocketCommands.valueOf(handlerMessage.command());
        final var event = this.createEventForCommand(command, request.topic(), session);
        this.publishEvent(event);
    }

    private ApplicationEvent createEventForCommand(final WebSocketCommands command, final String topic,
                                                   final WebSocketSession session) {
        return switch (command) {
            case SUBSCRIBE -> new SubscribeMeasureEvent(this, topic, session);
            case REMOVE -> new UnsubscribeMeasureEvent(this, topic, session);
            default -> new WebSocketDisconnectEvent(this, session);
        };
    }

    private void publishEvent(final ApplicationEvent event) {
        this.eventPublisher.publishEvent(event);
    }
}