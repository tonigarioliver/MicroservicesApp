package com.example.mqttclient.service;

public interface IMessageBroadcastingService<T, H> {
    public void listen(final T payload, final H header);
}
