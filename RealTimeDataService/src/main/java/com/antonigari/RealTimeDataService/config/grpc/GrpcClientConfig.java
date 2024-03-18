package com.antonigari.RealTimeDataService.config.grpc;

import com.antonigari.RealTimeDataService.GrpcDeviceTopicServiceGrpc;
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

    @Bean
    GrpcDeviceTopicServiceGrpc.GrpcDeviceTopicServiceBlockingStub grpcDeviceTopicServiceBlockingStub() {
        return GrpcDeviceTopicServiceGrpc.newBlockingStub(ManagedChannelBuilder
                .forAddress(this.grpcServerAddress, this.grpcServerPort)
                .usePlaintext()
                .build());
    }
}
