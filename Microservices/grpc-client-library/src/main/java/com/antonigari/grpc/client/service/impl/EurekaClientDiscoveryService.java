package com.antonigari.grpc.client.service.impl;

import com.antonigari.grpc.client.service.IClientsDiscoveryService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of IClientsDiscoveryService that uses Eureka for service discovery.
 */
@Service
@AllArgsConstructor
public class EurekaClientDiscoveryService implements IClientsDiscoveryService {

    private final DiscoveryClient discoveryClient;

    @Override
    public String getServiceUrl(final String serviceName) {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            return instances.get(0).getUri().toString();
        }
        return null;
    }

    @Override
    public String getHost(final String serviceName) {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            return instances.get(0).getHost();
        }
        return null;
    }

    @Override
    public Integer getPort(final String serviceName) {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            return instances.get(0).getPort();
        }
        return null;
    }
}