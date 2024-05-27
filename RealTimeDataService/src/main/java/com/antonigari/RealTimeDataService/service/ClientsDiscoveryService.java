package com.antonigari.RealTimeDataService.service;

public interface ClientsDiscoveryService {
    String getServiceUrl(String serviceName);

    String getHost(String serviceName);

    Integer getPort(String serviceName);
}
