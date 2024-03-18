package com.antonigari.RealTimeDataService.service;


import com.antonigari.RealTimeDataService.data.KafkaMessage;

public interface IKafkaService extends IMessageBroadcastingService<KafkaMessage, String, String> {
    boolean sendTopicDeviceMessageWithKafka(final String topic, final DeviceTopicDto deviceTopic);
}