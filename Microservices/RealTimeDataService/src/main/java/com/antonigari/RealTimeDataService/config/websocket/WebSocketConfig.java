package com.antonigari.RealTimeDataService.config.websocket;

import com.antonigari.RealTimeDataService.service.utilities.WebSocketHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Configuration class for WebSocket setup.
 * Configures WebSocket endpoints and handlers for real-time data communication.
 */
@Configuration
@EnableWebSocket
@AllArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private static final String WEBSOCKET_ENDPOINT = "/mqtt";
    private static final String ALLOWED_ORIGINS = "*";

    private final WebSocketHandler socketHandler;

    @Override
    public void registerWebSocketHandlers(final WebSocketHandlerRegistry registry) {
        registry.addHandler(this.socketHandler, WEBSOCKET_ENDPOINT)
                .setAllowedOrigins(ALLOWED_ORIGINS);
    }
}