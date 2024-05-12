package com.antonigari.RealTimeDataService.service.utilities;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketClientManager {
    private final ConcurrentHashMap<String, List<WebSocketSession>> webSocketClients = new ConcurrentHashMap<>();

    public void subscribe(final WebSocketSession session, final String topic) {
        this.webSocketClients.computeIfAbsent(topic, k -> new ArrayList<>()).add(session);
    }

    public List<WebSocketSession> getAllSessionsByTopic(final String topic) {
        return this.webSocketClients.entrySet().stream()
                .filter(entry -> entry.getKey().equals(topic))
                .flatMap(entry -> entry.getValue().stream())
                .toList();
    }

    public void removeWebSocketClientTopic(final WebSocketSession session, final String topic) {
        final List<WebSocketSession> sessions = this.webSocketClients.get(topic);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    public void removeWebSocketClientsWhenTopicRemoved(final String topic) {
        this.webSocketClients.remove(topic);
    }

    public void updateWebSocketClientWhenTopicUpdate(
            final String oldMeasurementDto,
            final String newMeasurementDto
    ) {
        this.webSocketClients.computeIfPresent(oldMeasurementDto, (dto, sessions) -> {
            this.webSocketClients.put(newMeasurementDto, sessions);
            this.webSocketClients.remove(dto);
            return sessions;
        });
    }


    public void removeWebSocketClient(final WebSocketSession session) {
        this.webSocketClients.values().forEach(sessions -> sessions.removeIf(session::equals));
    }

    public List<String> getKeysWithEmptyLists() {
        return this.webSocketClients.keySet().stream()
                .filter(key -> this.webSocketClients.get(key).isEmpty())
                .toList();
    }
}
