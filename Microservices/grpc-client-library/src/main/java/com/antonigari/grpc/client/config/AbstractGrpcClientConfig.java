package com.antonigari.grpc.client.config;

import com.antonigari.grpc.client.service.IClientsDiscoveryService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;

/**
 * Abstract base class for gRPC client configurations.
 * Provides common functionality for creating gRPC client stubs.
 */
public abstract class AbstractGrpcClientConfig {

    @Value(value = "${grpc.service.name}")
    protected String grpcServerServiceName;

    /**
     * Creates a managed channel for gRPC communication.
     *
     * @param clientsDiscoveryService the service discovery client
     * @return a configured ManagedChannel
     */
    protected ManagedChannel createManagedChannel(final IClientsDiscoveryService clientsDiscoveryService) {
        return ManagedChannelBuilder
                .forAddress(
                        clientsDiscoveryService.getHost(this.grpcServerServiceName),
                        clientsDiscoveryService.getPort(this.grpcServerServiceName)
                )
                .usePlaintext()
                .build();
    }
}