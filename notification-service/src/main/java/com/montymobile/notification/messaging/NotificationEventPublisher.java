package com.montymobile.notification.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.montymobile.dto.NotificationResponseDTO;
import com.montymobile.event.AbstractEventPublisher;

@Service
public class NotificationEventPublisher extends AbstractEventPublisher<NotificationResponseDTO> {

    public NotificationEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    // any additional behavior or parameters specific to NotificationEventPublisher
}