package com.montymobile.registration.controller.v1;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.montymobile.dto.APIResponse;
import com.montymobile.dto.TopicRequestDTO;
import com.montymobile.dto.TopicResponseDTO;
import com.montymobile.event.EventPublisher;
import com.montymobile.registration.service.RegistrationTokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/registrationtoken")
@RequiredArgsConstructor
@Slf4j
public class TopicRegistrationController {
	
	private final EventPublisher<TopicResponseDTO> topicRegistrationEventPublisher;
	private final RegistrationTokenService registrationTokenService;
	private static final String SUCCESS = "SUCCESS";
	
    @PostMapping
    public ResponseEntity<APIResponse<TopicResponseDTO>> handleSubscriptionRequest(@RequestBody @Valid TopicRequestDTO topicRequestDTO) 
    {
    	topicRequestDTO.setSubscriptionType(1);
    	TopicResponseDTO topicResponseDTO = registrationTokenService.createNewTopic(topicRequestDTO);
    	topicRegistrationEventPublisher.publishMessage(topicResponseDTO);
    	
        //Builder
        APIResponse<TopicResponseDTO> responseDTO = APIResponse
                .<TopicResponseDTO> builder()
                .status(SUCCESS)
                .results(topicResponseDTO)
                .build();

        log.info("TopicRegistrationController::handleSubscriptionRequest response {}", topicResponseDTO.toString());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    @DeleteMapping
    public ResponseEntity<APIResponse<TopicResponseDTO>> handleUnsubscribeRequest(@RequestBody @Valid TopicRequestDTO topicRequestDTO) 
    {
    	topicRequestDTO.setSubscriptionType(2);
    	TopicResponseDTO topicResponseDTO = registrationTokenService.unsubscribeTopic(topicRequestDTO);
    	topicRegistrationEventPublisher.publishMessage(topicResponseDTO);
    	
        //Builder
        APIResponse<TopicResponseDTO> responseDTO = APIResponse
                .<TopicResponseDTO> builder()
                .status("Deleted Succesfully!")
                .results(topicResponseDTO)
                .build();

        log.info("TopicRegistrationController::handleSubscriptionRequest response {}", topicResponseDTO.toString());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<APIResponse<List<TopicResponseDTO>>> getTokens() {

        List<TopicResponseDTO> tokens = registrationTokenService.getTokens();
        //Builder Design pattern (to avoid complex object creation headache)
        APIResponse<List<TopicResponseDTO>> responseDTO = APIResponse
                .<List<TopicResponseDTO>>builder()
                .status(SUCCESS)
                .results(tokens)
                .build();

        log.info("TopicRegistrationController::getTokens response {}", responseDTO.toString());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
