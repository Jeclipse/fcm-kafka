package com.montymobile.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicRequestDTO {

    @NotBlank(message = "topic name shouldn't be NULL OR EMPTY")
    private String topicName;

    @NotBlank(message = "token shouldn't be NULL OR EMPTY")
    private String token;
    private Integer subscriptionType;
    
}
