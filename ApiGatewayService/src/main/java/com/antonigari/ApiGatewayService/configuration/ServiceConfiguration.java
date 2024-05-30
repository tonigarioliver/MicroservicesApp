package com.antonigari.ApiGatewayService.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ServiceConfiguration {

    private final Map<Service, String> properties;

    public ServiceConfiguration(@Value("${services.grpcService.name}") final String grpcService,
                                @Value("${services.iotDeviceService.name}") final String iotDeviceService,
                                @Value("${services.RealTimeDataService.name}") final String realTimeDataService) {

        this.properties = Map.of(
                Service.valueOf(grpcService.toUpperCase()), grpcService,
                Service.valueOf(iotDeviceService.toUpperCase()), iotDeviceService,
                Service.valueOf(realTimeDataService.toUpperCase()), realTimeDataService
        );
    }

    public Map<Service, String> getProperties() {
        return this.properties;
    }
}