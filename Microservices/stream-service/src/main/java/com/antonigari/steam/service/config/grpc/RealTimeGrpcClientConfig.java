package com.antonigari.steam.service.config.grpc;

import com.antonigari.grpc.client.config.GrpcClientConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for gRPC clients.
 * This class extends the shared implementation from the GrpcClientLibrary.
 */
@Configuration
public class RealTimeGrpcClientConfig extends GrpcClientConfig {
    // All configuration is inherited from the shared GrpcClientConfig
}