package com.antonigari.RealTimeDataService.service.Impl;

import com.antonigari.grpcclient.service.impl.EurekaClientDiscoveryService;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

/**
 * Implementation of EurekaClientDiscoveryService for RealTimeDataService.
 * This class extends the shared implementation from the GrpcClientLibrary.
 */
@Service
public class EurekaClientDiscoveryServiceI extends EurekaClientDiscoveryService {

    public EurekaClientDiscoveryServiceI(DiscoveryClient discoveryClient) {
        super(discoveryClient);
    }
}
