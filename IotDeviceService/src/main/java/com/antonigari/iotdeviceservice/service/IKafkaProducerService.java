package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.data.model.KafkaMessage;

public interface IKafkaProducerService extends IMessageBroadcastingService<KafkaMessage> {
}