package com.montymobile.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDTO {
    
	//using HashSets to make sure I don't get duplicates in the request
    private Set<String> topics = new HashSet<>();
    
    @NotEmpty(message = "tokens should not be empty")
    private Set<@NotBlank(message = "token values must not be blank")String> tokens = new HashSet<>();
    
    @NotBlank(message = "title must not be blank")
    private String title;
    
    @NotBlank(message = "body must not be blank")
    private String body;
    
    private String condition;
    
}