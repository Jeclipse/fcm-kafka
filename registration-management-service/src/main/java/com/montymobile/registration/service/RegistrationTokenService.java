package com.montymobile.registration.service;

import java.util.List;

import com.montymobile.dto.TopicRequestDTO;
import com.montymobile.dto.TopicResponseDTO;

public interface RegistrationTokenService {
	
	TopicResponseDTO createNewTopic(TopicRequestDTO topicRequestDTO);
	TopicResponseDTO unsubscribeTopic(TopicRequestDTO topicRequestDTO);
	List<TopicResponseDTO> getTokens();

}
