package com.antonigari.ApiGatewayService.configuration;


import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GatewayConfig {
    private final AuthenticationFilter filter;

    @Bean
    public RouteLocator routes(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(this.filter).stripPrefix(1))
                        .uri("lb://USERSERVICE"))
                .route("iot-device-service", r -> r.path("/iotDeviceService/**")
                        .filters(f -> f.filter(this.filter).stripPrefix(1))
                        .uri("lb://IOTDEVICESERVICE"))
                .route("real-time-data-service", r -> r.path("/realTimeDataService/**")
                        .filters(f -> f.filter(this.filter).stripPrefix(1))
                        .uri("lb://REALTIMEDATASERVICE"))
                .build();
    }
}
