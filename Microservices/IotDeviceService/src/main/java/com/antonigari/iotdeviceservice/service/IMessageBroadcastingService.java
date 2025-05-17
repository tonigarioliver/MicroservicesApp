package com.antonigari.iotdeviceservice.service;

public interface IMessageBroadcastingService<T> {
    boolean sendMessage(final T payload);
}
