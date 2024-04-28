package com.antonigari.RealTimeDataService.service.utilities;

import com.antonigari.RealTimeDataService.model.DeviceMeasurementDto;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketClientManager {
    private final ConcurrentHashMap<DeviceMeasurementDto, List<WebSocketSession>> webSocketClients = new ConcurrentHashMap<>();

    public void subscribe(final WebSocketSession session, final DeviceMeasurementDto measurementDto) {
        this.webSocketClients.computeIfAbsent(measurementDto, k -> new ArrayList<>()).add(session);
    }

    public List<WebSocketSession> getAllSessionsByTopic(final String topic) {
        return this.webSocketClients.entrySet().stream()
                .filter(entry -> entry.getKey().topic().equals(topic))
                .flatMap(entry -> entry.getValue().stream())
                .toList();
    }

    public void removeWebSocketClientTopic(final WebSocketSession session, final DeviceMeasurementDto measurementDto) {
        final List<WebSocketSession> sessions = this.webSocketClients.get(measurementDto);
        if (sessions != null) {
            sessions.remove(session);
        }
    }

    public void removeWebSocketClientsWhenTopicRemoved(final DeviceMeasurementDto measurementDto) {
        this.webSocketClients.remove(measurementDto);
    }

    public void updateWebSocketClientWhenTopicUpdate(
            final DeviceMeasurementDto oldMeasurementDto,
            final DeviceMeasurementDto newMeasurementDto
    ) {
        this.webSocketClients.computeIfPresent(oldMeasurementDto, (dto, sessions) -> {
            this.webSocketClients.remove(dto);
            this.webSocketClients.put(newMeasurementDto, sessions);
            return sessions;
        });
    }


    public void removeWebSocketClient(final WebSocketSession session) {
        this.webSocketClients.values().forEach(sessions -> sessions.removeIf(session::equals));
    }

    public List<DeviceMeasurementDto> getKeysWithEmptyLists() {
        return this.webSocketClients.keySet().stream()
                .filter(key -> this.webSocketClients.get(key).isEmpty())
                .toList();
    }
}
