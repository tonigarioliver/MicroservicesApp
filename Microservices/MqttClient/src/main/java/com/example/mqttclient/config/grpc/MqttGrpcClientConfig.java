package com.example.mqttclient.config.grpc;

import com.antonigari.grpcclient.config.GrpcClientConfig;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for gRPC clients.
 * This class extends the shared implementation from the GrpcClientLibrary.
 */
@Configuration
public class MqttGrpcClientConfig extends GrpcClientConfig {
    // All configuration is inherited from the shared GrpcClientConfig
}