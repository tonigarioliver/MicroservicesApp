package com.antonigari.RealTimeDataService.service.utilities;

import com.antonigari.RealTimeDataService.config.websocket.WebSocketCommands;
import com.antonigari.RealTimeDataService.event.NewMeasureSubscriptionEvent;
import com.antonigari.RealTimeDataService.event.RemoveMeasureSubscriptionEvent;
import com.antonigari.RealTimeDataService.model.WebSocketHandlerMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        final ObjectMapper objectMapper = new ObjectMapper();
        final WebSocketHandlerMessage webSocketHandlerMessage = objectMapper.readValue(message.getPayload().toString(), WebSocketHandlerMessage.class);
        this.handleEventMessage(webSocketHandlerMessage, session);
        for (final WebSocketSession webSocketSession : this.webSocketSessions) {
            webSocketSession.sendMessage(message);
        }
    }


    private void handleEventMessage(final WebSocketHandlerMessage webSocketHandlerMessage,
                                    final WebSocketSession session) {
        switch (WebSocketCommands.valueOf(webSocketHandlerMessage.command())) {
            case SUBSCRIBE:
                this.eventPublisher.publishEvent(new NewMeasureSubscriptionEvent(this, webSocketHandlerMessage.payload(), session));
                break;
            case REMOVE:
                this.eventPublisher.publishEvent(new RemoveMeasureSubscriptionEvent(this, webSocketHandlerMessage.payload(), session));
                break;
            default:
                break;
        }
    }

}