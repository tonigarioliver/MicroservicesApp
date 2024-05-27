package com.antonigari.RealTimeDataService.config.grpc;

import com.antonigari.RealTimeDataService.DeviceMeasurementGrpcServiceGrpc;
import com.antonigari.RealTimeDataService.service.ClientsDiscoveryService;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    @Value(value = "${grpc.server.port}")
    int grpcServerPort;
    @Value(value = "${grpc.server.address}")
    String grpcServerAddress;
    String grpcServerServiceName = "GRPCSERVERSERVICE";

    @Bean
    DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceBlockingStub deviceMeasurementGrpcServiceBlockingStub(
            final ClientsDiscoveryService clientsDiscoveryService
    ) {
        System.out.println(String.format("This is a test of url %s", clientsDiscoveryService.getServiceUrl(this.grpcServerServiceName)));
        return DeviceMeasurementGrpcServiceGrpc.newBlockingStub(ManagedChannelBuilder
                .forAddress(
                        clientsDiscoveryService.getHost(this.grpcServerServiceName),
                        clientsDiscoveryService.getPort(this.grpcServerServiceName)
                )
                .usePlaintext()
                .build());
    }
}
