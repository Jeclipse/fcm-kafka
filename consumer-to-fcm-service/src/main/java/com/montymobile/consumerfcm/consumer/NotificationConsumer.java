package com.montymobile.consumerfcm.consumer;

import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.montymobile.consumerfcm.service.FCMService;
import com.montymobile.dto.NotificationResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {
	
	  private final FCMService<NotificationResponseDTO> fcmService;
		
	  //Kafka batch listener receives DTO messages in batches (List of DTO)
	  @KafkaListener(topics = "${appconstants.kafka.notification-topic}", 
		      groupId = "${notification.topic.group.id}",
		      containerFactory = "notificationKafkaListenerContainerFactory")
		  public void consume(@Payload List<NotificationResponseDTO> messages,
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
