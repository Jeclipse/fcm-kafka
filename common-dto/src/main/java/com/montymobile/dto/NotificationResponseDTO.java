package com.montymobile.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponseDTO {
	
	private UUID uuid;
	private Set<String> topics = new HashSet<>();
	private Set<String> tokens = new HashSet<>();
	private String condition;
	private String title;
	private String body;

}
