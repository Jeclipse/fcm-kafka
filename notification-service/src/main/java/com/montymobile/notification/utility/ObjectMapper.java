package com.montymobile.notification.utility;

import java.util.UUID;

import com.montymobile.dto.NotificationRequestDTO;
import com.montymobile.dto.NotificationResponseDTO;

public class ObjectMapper {
	
	private ObjectMapper()
	{
		throw new IllegalStateException("Utility class");
	}
	
	public static NotificationResponseDTO requestDTOtoResponseDTO(NotificationRequestDTO notificationRequestDTO)
	{
		NotificationResponseDTO notificationResponseDTO = new NotificationResponseDTO();
		notificationResponseDTO.setUuid(UUID.randomUUID());
		notificationResponseDTO.setCondition(notificationRequestDTO.getCondition());
		notificationResponseDTO.setTokens(notificationRequestDTO.getTokens());
		notificationResponseDTO.setTopics(notificationRequestDTO.getTopics());
		notificationResponseDTO.setTitle(notificationRequestDTO.getTitle());
		notificationResponseDTO.setBody(notificationRequestDTO.getBody());

		return notificationResponseDTO;
	}

}
