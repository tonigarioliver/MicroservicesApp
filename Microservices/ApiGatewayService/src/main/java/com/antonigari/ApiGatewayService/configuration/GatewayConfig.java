package com.antonigari.ApiGatewayService.configuration;


import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ApiGatewayRoutesConfig.class)
@AllArgsConstructor
public class GatewayConfig {
    private final AuthenticationFilter authenticationFilter;
    private final ApiGatewayRoutesConfig apiGatewayRoutesConfig;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        RouteLocatorBuilder.Builder routesBuilder = builder.routes();

        apiGatewayRoutesConfig.getRoutes().forEach(route ->
            routesBuilder.route(route.id(), r -> r.path(route.path())
                    .filters(f -> getGatewayFilterSpec(route, f))
                    .uri(route.uri()))
        );

        return routesBuilder.build();
    }

    private GatewayFilterSpec getGatewayFilterSpec(
            final ApiGatewayRoutesConfig.RouteDefinition route,
            final GatewayFilterSpec f) {
        if (route.applyJwtFilter()) {
            f.filter(this.authenticationFilter);
        }
        return f.stripPrefix(1);
    }
}
