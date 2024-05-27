package com.antonigari.GrpcServerService.configuration.grpc;

import net.devh.boot.grpc.server.autoconfigure.GrpcServerAutoConfiguration;
import net.devh.boot.grpc.server.config.GrpcServerProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcConfig {
    @Value(value = "${grpc.server.port}")
    int grpcServerPort;

    @Bean
    public GrpcServerAutoConfiguration grpcServerAutoConfiguration() {
        return new GrpcServerAutoConfiguration();
    }

    @Bean
    public GrpcServerProperties grpcServerProperties() {
        final GrpcServerProperties grpcServerProperties = new GrpcServerProperties();
        grpcServerProperties.setPort(this.grpcServerPort);
        return grpcServerProperties;
    }
}
