package com.antonigari.steam.service.service.Impl;

import com.antonigari.grpc.client.service.impl.EurekaClientDiscoveryService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

/**
 * Implementation of EurekaClientDiscoveryService for RealTimeDataService.
 * This class extends the shared implementation from the GrpcClientLibrary.
 */
@Service
public class EurekaClientDiscoveryServiceI extends EurekaClientDiscoveryService {

    public EurekaClientDiscoveryServiceI(final DiscoveryClient discoveryClient) {
        super(discoveryClient);
    }
}
