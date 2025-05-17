package com.antonigari.iotdeviceservice.configuration.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(final String topic, final String message) {
        this.kafkaTemplate.send(topic, message);
    }
}
