package com.example.mqttclient.config.grpc;

import com.antonigari.MqttClient.DeviceMeasurementGrpcServiceGrpc;
import com.example.mqttclient.service.IClientsDiscoveryService;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcClientConfig {
    @Value(value = "${grpc.service.name}")
    String grpcServerServiceName;

    @Bean
    DeviceMeasurementGrpcServiceGrpc.DeviceMeasurementGrpcServiceBlockingStub deviceMeasurementGrpcServiceBlockingStub(
            final IClientsDiscoveryService clientsDiscoveryService
    ) {
        return DeviceMeasurementGrpcServiceGrpc.newBlockingStub(ManagedChannelBuilder
                .forAddress(
                        clientsDiscoveryService.getHost(this.grpcServerServiceName),
                        clientsDiscoveryService.getPort(this.grpcServerServiceName)
                )
                .usePlaintext()
                .build());
    }
}
