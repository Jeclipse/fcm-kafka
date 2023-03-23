package com.montymobile.notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	
	@Value("${appconstants.kafka.registration-topic}")
	private String registrationTopic;
	
	@Value("${appconstants.kafka.topic.partitions}")
	Integer partitions;

    //defaults to 1 replica and 1 partition, using 3 partitions along with concurrency level 3 in the consumers
	//In an enterprise / distributed scenario, would use 3 replicas as well along with 2 additional brokers added to docker-compose
	@Bean
    NewTopic topic()
	{
	  return TopicBuilder.name(registrationTopic)
			  .partitions(partitions)
					.build();
	}
	
	

}
