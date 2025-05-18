package com.antonigari.grpc.client.config;

import com.antonigari.grpc.client.service.IClientsDiscoveryService;
import com.antonigari.grpc.server.service.DeviceMeasurementGrpcServiceGrpc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for gRPC clients.
 * This is a shared implementation that can be used by multiple services.
 */
@Configuration
public class GrpcClientConfig extends AbstractGrpcClientConfig {

    /**
     * Creates a blocking stub for the DeviceMeasurementGrpcService.
     *
     * @param clientsDiscoveryService the service discovery client
     * @return a configured blocking stub
     */
    @Bean
    DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceBlockingStub deviceMeasurementGrpcServiceBlockingStub(
            final IClientsDiscoveryService clientsDiscoveryService
    ) {
        return DeviceMeasurementGrpcServiceGrpc.newBlockingStub(
                this.createManagedChannel(clientsDiscoveryService)
        );
    }
}