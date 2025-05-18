package com.antonigari.api.gateway.service.service.impl;

import com.antonigari.api.gateway.service.service.IClientsDiscoveryService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EurekaClientDiscoveryService implements IClientsDiscoveryService {

    private DiscoveryClient discoveryClient;

    @Override
    public String getServiceUrl(final String serviceName) {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            instances.getFirst().getUri();
            return instances.getFirst().getUri().toString();
        }
        return null;
    }

    @Override
    public String getHost(final String serviceName) {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            return instances.getFirst().getHost();
        }
        return null;
    }

    @Override
    public Integer getPort(final String serviceName) {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            return instances.getFirst().getPort();
        }
        return null;
    }
}
