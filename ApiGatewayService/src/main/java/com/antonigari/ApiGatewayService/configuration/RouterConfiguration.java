package com.antonigari.ApiGatewayService.configuration;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class RouterConfiguration {
    private final ServiceConfiguration serviceConfiguration;

    @Bean
    public RouteLocator customRouteLocator(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route("service1", r -> r.path("/service1/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://SERVICE-NAME1"))
                .route("service2", r -> r.path("/service2/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://SERVICE-NAME2"))
                .build();
    }
}
