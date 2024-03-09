package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.data.model.KafkaMessage;
import com.antonigari.iotdeviceservice.model.DeviceTopicDto;

public interface IKafkaProducerService extends IMessageBroadcastingService<KafkaMessage> {
    boolean sendDeviceTopicMessageToKafka(final String topic, final DeviceTopicDto deviceTopic);
}