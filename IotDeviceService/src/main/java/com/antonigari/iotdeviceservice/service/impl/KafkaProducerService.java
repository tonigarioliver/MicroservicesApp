package com.antonigari.iotdeviceservice.service.impl;

import com.antonigari.iotdeviceservice.configuration.kafka.MessageProducer;
import com.antonigari.iotdeviceservice.data.model.KafkaMessage;
import com.antonigari.iotdeviceservice.service.IKafkaProducerService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class KafkaProducerService implements IKafkaProducerService {

    private final MessageProducer messageProducer;
    private final KafkaTemplate<String, String> kafkaTemplate;

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
}