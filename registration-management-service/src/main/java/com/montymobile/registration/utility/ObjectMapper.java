package com.montymobile.registration.utility;

import java.util.UUID;

import com.montymobile.dto.TopicRequestDTO;
import com.montymobile.dto.TopicResponseDTO;
import com.montymobile.registration.domain.RegistrationToken;

public class ObjectMapper {
	
	private ObjectMapper()
	{
		throw new IllegalStateException("Utility class");
	}
	
	public static TopicResponseDTO requestDTOtoResponseDTO(TopicRequestDTO topicRequestDTO)
	{
		TopicResponseDTO topicResponseDTO = new TopicResponseDTO();
		topicResponseDTO.setUuid(UUID.randomUUID());
		topicResponseDTO.setToken(topicRequestDTO.getToken());
		topicResponseDTO.setTopicName(topicRequestDTO.getTopicName());
		topicResponseDTO.setSubscriptionType(topicRequestDTO.getSubscriptionType());

		return topicResponseDTO;
	}
	
	public static RegistrationToken requestDTOToEntity(TopicRequestDTO topicRequestDTO)
	{
		RegistrationToken token = new RegistrationToken();
		token.setUuid(UUID.randomUUID());
		token.setToken(topicRequestDTO.getToken());
		token.setTopic(topicRequestDTO.getTopicName());
		token.setSubscriptionType(topicRequestDTO.getSubscriptionType());

		return token;
	}
	
	public static TopicResponseDTO entityToResponseDTO(RegistrationToken token)
	{
		TopicResponseDTO topicResponseDTO = new TopicResponseDTO();
		topicResponseDTO.setUuid(token.getUuid());
		topicResponseDTO.setToken(token.getToken());
		topicResponseDTO.setTopicName(token.getTopic());
		topicResponseDTO.setSubscriptionType(token.getSubscriptionType());

		return topicResponseDTO;
	}

}
