package com.montymobile.consumerfcm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.montymobile.consumerfcm.service.FCMService;
import com.montymobile.dto.NotificationResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationToFCMService implements FCMService<NotificationResponseDTO> 
{
	
	  private final FirebaseMessaging fcm;

	   @Override
	    public void sendToFCM(List<NotificationResponseDTO> messages) 
	   {

	        for (NotificationResponseDTO message : messages) 
	        {
   
	            Notification notification = Notification.builder()
	                    .setTitle(message.getTitle())
	                    .setBody(message.getBody())
	                    .build();
	            
	            
	            MulticastMessage.Builder multicastMessageBuilder = MulticastMessage.builder()
	                    .addAllTokens(message.getTokens())
	                    .setNotification(notification);
	            
          try {
        	    
	            if (!message.getCondition().isEmpty()) 
	            {
	            	
	                    Message messageToSend = Message.builder()
	                            .setNotification(notification)
	                            .setCondition(message.getCondition())
	                            .build();

	                    String response = fcm.send(messageToSend);
	                    log.info("Successfully sent message with condition: {}", response);
	            
	            } 
	            else if (!message.getTopics().isEmpty())
	            {
	            	List<Message> topicMessages = new ArrayList<>();
	                for (String topic : message.getTopics()) 
	                {
	                  
	                	topicMessages.add(Message.builder()
	                                .setTopic(topic)
	                                .setNotification(notification)
	                                .build());
	                	
	                }
	                
                	BatchResponse response = fcm.sendAll(topicMessages);
                	log.info(response.getSuccessCount() + " topic messages were sent successfully");
	            }

	            
	                MulticastMessage multicastMessage = multicastMessageBuilder.build();
	                BatchResponse response = fcm.sendMulticast(multicastMessage);
	                log.info(response.getSuccessCount() + " token messages were sent successfully");
	                

		  } catch (FirebaseMessagingException e) {
			  log.error("Firebase messaging exception! '{}'", e.getMessage());
          }
	     }

	    }
}