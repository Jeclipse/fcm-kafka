package com.montymobile.consumerfcm.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.TopicManagementResponse;
import com.montymobile.consumerfcm.service.FCMService;
import com.montymobile.dto.TopicResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
@RequiredArgsConstructor
public class TopicToFCMService implements FCMService<TopicResponseDTO> {
	
	  private final FirebaseMessaging fcm;

	@Override
	public void sendToFCM(List<TopicResponseDTO> messages) {
		
	   //Group the tokens [V] by topicName-SubscriptionType [K] and subscription type into a map 
	   //since the FCM subscribe/unsubscribe method takes all tokens subscribed/unsubscribed to the same topic in batches
	   Map<String, List<String>> tokensByTopic = messages.stream()
	            .collect(Collectors.groupingBy(topic -> topic.getTopicName() + "-" + topic.getSubscriptionType(),
	                    Collectors.mapping(TopicResponseDTO::getToken, Collectors.toList())));

	    //Process each topic asynchronously by iterating over a ConcurrentHashMap
	    ConcurrentHashMap<String, List<String>> concurrentTokensByTopic = new ConcurrentHashMap<>(tokensByTopic);

	    concurrentTokensByTopic.forEach((topic, tokens) -> {
	    	
	        String[] topicParts = topic.split("-");
	        String topicName = topicParts[0];
	        int subscriptionType = Integer.parseInt(topicParts[1]);
	        
	        if (subscriptionType == 1) 
	        {

	            log.info("Subscribing to topic '{}' with tokens: {}", topicName, tokens);
	            ApiFuture<TopicManagementResponse> future = fcm.subscribeToTopicAsync(tokens, topicName);
	            ApiFutures.addCallback(future, new ApiFutureCallback<TopicManagementResponse>() {
	                @Override
	                public void onSuccess(TopicManagementResponse response) {
	                    log.info("Subscribe response success count: {}", response.getSuccessCount());
	                }

	                @Override
	                public void onFailure(Throwable throwable) {
	                    log.error("Failed to subscribe to topic '{}'", topicName, throwable);
	                }
	            }, MoreExecutors.directExecutor());
	        } else 
	        {

	            log.info("Unsubscribing from topic '{}' with tokens: {}", topicName, tokens);
	            ApiFuture<TopicManagementResponse> future = fcm.unsubscribeFromTopicAsync(tokens, topicName);
	            ApiFutures.addCallback(future, new ApiFutureCallback<TopicManagementResponse>() {
	                @Override
	                public void onSuccess(TopicManagementResponse response) {
	                    log.info("Unsubscribe response success count: {}", response.getSuccessCount());
	                }

	                @Override
	                public void onFailure(Throwable throwable) {
	                    log.error("Failed to unsubscribe from topic '{}'", topicName, throwable);
	                }
	            }, MoreExecutors.directExecutor());
	        }
	    });
	}

}
