package com.montymobile.registration.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.montymobile.dto.TopicRequestDTO;
import com.montymobile.dto.TopicResponseDTO;
import com.montymobile.exception.TokenServiceBusinessException;
import com.montymobile.registration.domain.RegistrationToken;
import com.montymobile.registration.repository.RegistrationTokenRepository;
import com.montymobile.registration.service.RegistrationTokenService;
import com.montymobile.registration.utility.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationTokenServiceImpl implements RegistrationTokenService {
	
    private final RegistrationTokenRepository tokenRepository;


    public TopicResponseDTO createNewTopic(TopicRequestDTO topicRequestDTO) {
    	TopicResponseDTO topicResponseDTO;

        try {
            log.info("RegistrationTokenService:createNewTopic execution started.");
            
            // Check if a token with the same topic already exists
            Optional<RegistrationToken> existingTokenOptional = tokenRepository.findByTopicAndToken(topicRequestDTO.getTopicName(), topicRequestDTO.getToken());
            if (existingTokenOptional.isPresent()) {
                throw new TokenServiceBusinessException("A token with the same topic already exists");
            }
            
            RegistrationToken token = ObjectMapper.requestDTOToEntity(topicRequestDTO);

            RegistrationToken tokenResults = tokenRepository.save(token);
            
            topicResponseDTO = ObjectMapper.entityToResponseDTO(tokenResults);

        } catch (Exception ex) {
            log.error("Exception occurred while persisting token to database , Exception message {}", ex.getMessage());
            throw new TokenServiceBusinessException(ex.getMessage());
        }
        log.info("RegistrationTokenService:createNewTopic execution ended.");
        return topicResponseDTO;
    }
    
    public TopicResponseDTO unsubscribeTopic(TopicRequestDTO topicRequestDTO) {
    	TopicResponseDTO topicResponseDTO;

        try {
            log.info("RegistrationTokenService:createNewTopic execution started.");
            
            // Check if a token with the same topic already exists
            Optional<RegistrationToken> existingTokenOptional = tokenRepository.findByTopicAndToken(topicRequestDTO.getTopicName(), topicRequestDTO.getToken());
            if (existingTokenOptional.isEmpty()) {
                throw new TokenServiceBusinessException("Topic has already been ubsubscribed to");
            }
            
            RegistrationToken token = existingTokenOptional.get();
            
            tokenRepository.delete(token);
            
            topicResponseDTO = ObjectMapper.entityToResponseDTO(token);
            

        } catch (Exception ex) {
            log.error("Exception occurred while deleteing token from database , Exception message {}", ex.getMessage());
            throw new TokenServiceBusinessException(ex.getMessage());
        }
        log.info("RegistrationTokenService:unsubscribeTopic execution ended.");
        return topicResponseDTO;
    }

    @Cacheable(value = "token")
    public List<TopicResponseDTO> getTokens() {
        List<TopicResponseDTO> topicResponseDTOS = null;

        try {
            log.info("RegistrationTokenService:getTokens execution started.");

            List<RegistrationToken> tokenList = tokenRepository.findAll();

            if (!tokenList.isEmpty()) {
                topicResponseDTOS = tokenList.stream()
                        .map(ObjectMapper::entityToResponseDTO)
                        .toList();
            } else {
                topicResponseDTOS = Collections.emptyList();
            }


        } catch (Exception ex) {
            log.error("Exception occurred while retrieving tokens from database , Exception message {}", ex.getMessage());
            throw new TokenServiceBusinessException("Exception occurred while fetching all tokens from the database");
        }

        log.info("RegistrationTokenService:getTokens execution ended.");
        return topicResponseDTOS;
    }

}
