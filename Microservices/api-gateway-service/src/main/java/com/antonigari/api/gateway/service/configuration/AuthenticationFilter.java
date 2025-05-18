package com.antonigari.api.gateway.service.configuration;

import com.antonigari.api.gateway.service.service.IClientsDiscoveryService;
import com.antonigari.api.gateway.service.service.impl.RouterValidatorService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@RefreshScope
@Component
@AllArgsConstructor
public class AuthenticationFilter implements GatewayFilter {

    private final ServicesConfiguration servicesConfiguration;
    private final RouterValidatorService validator;
    private final WebClient webClient;
    private final IClientsDiscoveryService clientsDiscoveryService;

    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        final ServerHttpRequest request = exchange.getRequest();

        if (this.validator.isSecured.test(request)) {
            if (this.authMissing(request)) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED, "token missing");
            }
            return this.webClient.get()
                    .uri(this.buildValidationAuthUri(request))
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .flatMap(isValidToken -> this.checkAuthResponse(exchange, chain, isValidToken))
                    .onErrorResume(e -> this.onError(exchange, HttpStatus.UNAUTHORIZED, "token not valid"));
        }
        return chain.filter(exchange);
    }

    private Mono<Void> checkAuthResponse(final ServerWebExchange exchange, final GatewayFilterChain chain,
                                         final Boolean isValidToken) {
        return Boolean.TRUE.equals(isValidToken)
                ? chain.filter(exchange)
                : this.onError(exchange, HttpStatus.UNAUTHORIZED, "Token NOT valid");
    }

    private String buildValidationAuthUri(final ServerHttpRequest request) {
        return String.format(
                "%s/api/v1/user/isValid/%s/%s",
                this.getAuthServiceUrl(),
                this.getAuthorizationToken(request),
                this.getUserName(request)
        );
    }

    private String getAuthServiceUrl() {
        return String.format(
                "http://%s:%s",
                this.clientsDiscoveryService.getHost(this.servicesConfiguration.getUserServiceName()),
                this.clientsDiscoveryService.getPort(this.servicesConfiguration.getUserServiceName())
        );
    }

    private String getUserName(final ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("UserName").getFirst();
    }

    private String getAuthorizationToken(final ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").getFirst().substring(7);
    }

    private Mono<Void> onError(final ServerWebExchange exchange, final HttpStatus status, final String errorMessage) {
        final ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        final byte[] bytes = errorMessage.getBytes(StandardCharsets.UTF_8);
        final DataBuffer buffer = new DefaultDataBufferFactory().wrap(bytes);
        response.getHeaders().add("Content-Type", "application/json");
        return response.writeWith(Mono.just(buffer));
    }

    private boolean authMissing(final ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }
}