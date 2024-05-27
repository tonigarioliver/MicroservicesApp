package com.antonigari.RealTimeDataService.service.Impl;

import com.antonigari.RealTimeDataService.service.ClientsDiscoveryService;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EurekaClientDiscovery implements ClientsDiscoveryService {

    private DiscoveryClient discoveryClient;

    @Override
    public String getServiceUrl(final String serviceName) {
        final List<ServiceInstance> instances = this.discoveryClient.getInstances(serviceName);
        if (instances != null && !instances.isEmpty()) {
            instances.get(0).getUri();
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
