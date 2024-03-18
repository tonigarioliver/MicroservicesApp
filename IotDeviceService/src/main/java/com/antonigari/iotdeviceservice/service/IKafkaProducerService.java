package com.antonigari.iotdeviceservice.service;

import com.antonigari.iotdeviceservice.data.model.KafkaMessage;
import com.antonigari.iotdeviceservice.data.model.KafkaProducerTopic;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;

public interface IKafkaProducerService extends IMessageBroadcastingService<KafkaMessage> {
    boolean sendDeviceMeasurementStatus(final KafkaProducerTopic topic, final DeviceMeasurementDto deviceMeasurementDto);
}