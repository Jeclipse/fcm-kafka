package com.montymobile.consumerfcm.consumer;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.montymobile.consumerfcm.service.FCMService;
import com.montymobile.dto.TopicResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegistrationConsumer
  {

	  private final FCMService<TopicResponseDTO> fcmService;
		
	  //Kafka batch listener receives DTO messages in batches (List of  TopicResponseDTO)
	  @KafkaListener(topics = "${appconstants.kafka.registration-topic}", 
		      groupId = "${registration.topic.group.id}",
		      containerFactory = "registrationKafkaListenerContainerFactory")
		  public void consume(@Payload List<TopicResponseDTO> messages,
				              @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
				              @Header(KafkaHeaders.OFFSET) List<Long> offsets)
	      {

		    log.info("Messages received: {}, number of token requests received: {}, partitions:{} and offsets: {}",
				messages.toString(),
                messages.size(),
                partitions.toString(),
                offsets.toString());
		  
		    fcmService.sendToFCM(messages);	    

		  }
	  

  }
