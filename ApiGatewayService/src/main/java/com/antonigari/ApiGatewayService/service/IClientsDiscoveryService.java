package com.antonigari.ApiGatewayService.service;

public interface IClientsDiscoveryService {
    String getServiceUrl(String serviceName);

    String getHost(String serviceName);

    Integer getPort(String serviceName);
}
