package com.montymobile.registration.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.montymobile.dto.TopicResponseDTO;
import com.montymobile.event.AbstractEventPublisher;

@Service
public class TopicRegistrationEventPublisher extends AbstractEventPublisher<TopicResponseDTO> {

    public TopicRegistrationEventPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        super(kafkaTemplate);
    }

    // any additional behavior or parameters specific to TopicRegistrationEventPublisher
}