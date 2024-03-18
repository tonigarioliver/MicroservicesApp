package com.antonigari.RealTimeDataService.service;

public interface IMessageBroadcastingService<SendMessage, ReceiveMessage, HeaderReceiveMessage> {
    public void listen(final ReceiveMessage payload, final HeaderReceiveMessage header);

    boolean sendMessage(final SendMessage payload);
}
