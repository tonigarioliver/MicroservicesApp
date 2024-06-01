package com.antonigari.ApiGatewayService.configuration;

import com.antonigari.ApiGatewayService.service.IClientsDiscoveryService;
import com.antonigari.ApiGatewayService.service.impl.RouterValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RefreshScope
@Component
@AllArgsConstructor
public class AuthenticationFilter implements GatewayFilter {
    private RouterValidatorService validator;
    private final WebClient webClient;
    private final IClientsDiscoveryService clientsDiscoveryService;

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();

        if (this.validator.isSecured.test(request)) {
            if (this.authMissing(request)) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }
            //@Value("${services.userService.name}")
            final String USER_SERVICE_NAME = "USERSERVICE";
            final String token = request.getHeaders().getOrEmpty("Authorization").get(0);
            final String userName = request.getHeaders().getOrEmpty("UserName").get(0);
            final String userServiceUrl = String.format(
                    "http://%s:%s",
                    this.clientsDiscoveryService.getHost(USER_SERVICE_NAME),
                    this.clientsDiscoveryService.getPort(USER_SERVICE_NAME)
            );
            return this.webClient.get()
                    .uri(String.format("%s/%s/%s", userServiceUrl, token, userName))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isValidToken -> {
                        if (isValidToken) {
                            return this.onError(exchange, HttpStatus.UNAUTHORIZED);
                        }
                        return chain.filter(exchange);
                    });
        }
        return chain.filter(exchange);
    }

    private Mono<Void> onError(final ServerWebExchange exchange, final HttpStatus httpStatus) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private boolean authMissing(final ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}