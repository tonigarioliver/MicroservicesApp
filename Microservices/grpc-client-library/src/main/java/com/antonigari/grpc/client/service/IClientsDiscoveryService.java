package com.antonigari.grpc.client.service;

/**
 * Interface for service discovery operations.
 * Used to locate services by name and retrieve their connection details.
 */
public interface IClientsDiscoveryService {
    /**
     * Gets the full service URL for a service by name.
     *
     * @param serviceName the name of the service to locate
     * @return the full URL of the service
     */
    String getServiceUrl(String serviceName);

    /**
     * Gets the host for a service by name.
     *
     * @param serviceName the name of the service to locate
     * @return the host of the service
     */
    String getHost(String serviceName);

    /**
     * Gets the port for a service by name.
     *
     * @param serviceName the name of the service to locate
     * @return the port of the service
     */
    Integer getPort(String serviceName);
}