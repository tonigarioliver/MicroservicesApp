package com.antonigari.api.gateway.service.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "api-gateway")
public class ApiGatewayRoutesConfig {

    private List<RouteDefinition> routes;

    public record RouteDefinition(String id, String path, String uri, boolean applyJwtFilter) {
    }
}

