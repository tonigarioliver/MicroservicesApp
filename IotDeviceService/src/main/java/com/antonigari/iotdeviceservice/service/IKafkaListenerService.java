package com.antonigari.iotdeviceservice.service;

public interface IKafkaListenerService<ReceiveMessage, HeaderReceiveMessage> {
    public void listen(final ReceiveMessage payload, final HeaderReceiveMessage header);
}
