package com.montymobile.notification.controller.v1;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.montymobile.dto.APIResponse;
import com.montymobile.dto.NotificationRequestDTO;
import com.montymobile.dto.NotificationResponseDTO;
import com.montymobile.event.EventPublisher;
import com.montymobile.notification.utility.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {
	
	private final EventPublisher<NotificationResponseDTO> notificationEventPublisher;
	
    @PostMapping
    public ResponseEntity<APIResponse<NotificationResponseDTO>> handleNotificationRequest(@RequestBody @Valid NotificationRequestDTO notificationRequestDTO) 
    {
    	NotificationResponseDTO notificationResponseDTO = ObjectMapper.requestDTOtoResponseDTO(notificationRequestDTO);
    	notificationEventPublisher.publishMessage(notificationResponseDTO);
    	
        //Builder
        APIResponse<NotificationResponseDTO> responseDTO = APIResponse
                .<NotificationResponseDTO> builder()
                .status("SUCCESS")
                .results(notificationResponseDTO)
                .build();

        log.info("NotificationController::handleSubscriptionRequest response {}", notificationResponseDTO.toString());

        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

}
