package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.configuration.kafka.MessageProducer;
import com.antonigari.iotdeviceservice.data.model.KafkaMessage;
import com.antonigari.iotdeviceservice.data.model.KafkaProducerTopic;
import com.antonigari.iotdeviceservice.model.DeviceMeasurementDto;
import com.antonigari.iotdeviceservice.service.IKafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaProducerService implements IKafkaProducerService {

    private final MessageProducer messageProducer;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public boolean sendMessage(final KafkaMessage kafkaMessage) {
        final CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(kafkaMessage.getTopic(), kafkaMessage.getMessage());
        try {
            final SendResult<String, String> result = future.get();

            System.out.println("Sent message=[" + kafkaMessage.getMessage() +
                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
            return true;
        } catch (final InterruptedException | ExecutionException e) {
            System.out.println("Unable to send message=[" +
                    kafkaMessage.getMessage() + "] due to : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean sendDeviceMeasurementStatus(final KafkaProducerTopic topic, final DeviceMeasurementDto deviceMeasuremen) {
        try {
            final String payload = this.objectMapper.writeValueAsString(deviceMeasuremen);
            return this.sendMessage(KafkaMessage.builder()
                    .topic(topic.name())
                    .message(payload)
                    .build());
        } catch (final JsonProcessingException e) {
            log.error(e.getMessage());
            return false;
        }
    }
}