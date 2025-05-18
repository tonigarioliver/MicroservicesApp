package com.antonigari.steam.service.config.kafka;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * Component responsible for producing messages to Kafka topics.
 * Provides asynchronous message sending capabilities with error handling.
 */
@Component
@AllArgsConstructor
public final class MessageProducer {
    private static final Logger logger = LoggerFactory.getLogger(MessageProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends a message to the specified Kafka topic asynchronously.
     *
     * @param topic   the topic to send the message to
     * @param message the message content
     * @return CompletableFuture containing the result of the send operation
     */
    public CompletableFuture<SendResult<String, String>> sendMessage(final String topic, final String message) {
        logger.debug("Sending message to topic {}: {}", topic, message);

        return this.kafkaTemplate.send(topic, message)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        logger.debug("Message sent successfully to topic {}", topic);
                    } else {
                        logger.error("Failed to send message to topic {}", topic, ex);
                    }
                });
    }
}