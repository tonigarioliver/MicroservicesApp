package com.antonigari.steam.service.service;

public interface IMessageBroadcastingService<T, H> {
    public void listen(final T payload, final H header);
}
