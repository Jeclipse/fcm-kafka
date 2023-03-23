package com.montymobile.consumerfcm.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.montymobile.dto.NotificationResponseDTO;
import com.montymobile.dto.TopicResponseDTO;

@Configuration
public class KafkaConsumerConfig 
{
  @Value(value = "${appconstants.kafka.bootstrapAddress}")
  private String bootstrapAddress;
 
  @Value(value = "${registration.topic.group.id}")
  private String registrationGroupId;
 
  @Value(value = "${notification.topic.group.id}")
  private String notificationGroupId;
  
  @Value(value = "${appconstants.kafka.max-poll-records}")
  private String maxPollRecords;
  
  @Value(value = "${appconstants.kafka.concurrency-level}")
  private Integer concurrencyLevel;
 
  //first consumer for the registration topic
 
  
  //We don't need to set key serializer and value deserializer here because Kafka automatically infers them
  //from the DefaultKafkaConsumerFactory constructor
  @Bean
  ConsumerFactory<String, TopicResponseDTO> registrationConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, registrationGroupId);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
    return new DefaultKafkaConsumerFactory<>(props, 
            new StringDeserializer(), 
            new JsonDeserializer<>(TopicResponseDTO.class));
  }
  
  //For the topic registration listener, we configure a batch listener so that we can send subscriptions to FCM in batches.
  //The subscribeToTopic FCM method accepts a list of registration tokens so this approach is perfect to prevent overwhelming
  //The FCM back-end with too many separate requests
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, TopicResponseDTO>  registrationKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, TopicResponseDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(registrationConsumerFactory());
    factory.setBatchListener(true);
    factory.setConcurrency(concurrencyLevel);
    return factory;
  }
 
  //second consumer for notification topic
  @Bean
  ConsumerFactory<String, NotificationResponseDTO> notificationConsumerFactory() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, notificationGroupId);
    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
    return new DefaultKafkaConsumerFactory<>(props, 
        new StringDeserializer(), 
        new JsonDeserializer<>(NotificationResponseDTO.class));
  }
 
  
  @Bean
  ConcurrentKafkaListenerContainerFactory<String, NotificationResponseDTO>  notificationKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, NotificationResponseDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(notificationConsumerFactory());
    factory.setBatchListener(true);
    factory.setConcurrency(concurrencyLevel);
    return factory;
  }
}