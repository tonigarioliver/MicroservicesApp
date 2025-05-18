package com.antonigari.grpcclient.config;

import com.antonigari.grpcclient.grpc.DeviceMeasurementGrpcServiceGrpc;
import com.antonigari.grpcclient.service.IClientsDiscoveryService;
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
                createManagedChannel(clientsDiscoveryService)
        );
    }
}