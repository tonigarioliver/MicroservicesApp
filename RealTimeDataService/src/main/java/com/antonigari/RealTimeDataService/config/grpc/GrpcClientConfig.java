package com.antonigari.RealTimeDataService.config.grpc;

import com.antonigari.RealTimeDataService.DeviceMeasurementGrpcServiceGrpc;
import com.antonigari.RealTimeDataService.service.IClientsDiscoveryService;
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
            final IClientsDiscoveryService IClientsDiscoveryService
    ) {
        return DeviceMeasurementGrpcServiceGrpc.newBlockingStub(ManagedChannelBuilder
                .forAddress(IClientsDiscoveryService.getHost(this.grpcServerServiceName)
                        ,
                        IClientsDiscoveryService.getPort(this.grpcServerServiceName)
                )
                .usePlaintext()
                .build());
    }
}
