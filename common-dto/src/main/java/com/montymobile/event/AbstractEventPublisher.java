package com.montymobile.event;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractEventPublisher<T> implements EventPublisher<T> {

    @Value("${appconstants.kafka.registration-topic}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public void publishMessage(T message) {
        log.info(String.format("EventPublisher Sent: %s", message));
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Received new metadata. \n" +
                        "Topic:" + result.getRecordMetadata().topic() + "\n" +
                        "Partition: " + result.getRecordMetadata().partition() + "\n" +
                        "Offset: " + result.getRecordMetadata().offset() + "\n" +
                        "Timestamp: " + result.getRecordMetadata().timestamp());
            } else {
                log.error("Failed to send message: " + ex.getMessage());
            }
        });
    }
}
