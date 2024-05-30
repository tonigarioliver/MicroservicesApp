package com.antonigari.ApiGatewayService.configuration;

public enum Service {
    GRPCSERVERSERVICE,
    IOTDEVICESERVICE,
    REALTIMEDATASERVICE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
