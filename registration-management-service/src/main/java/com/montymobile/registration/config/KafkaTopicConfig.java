package com.montymobile.registration.config;

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

    //defaults to 1 replica and 1 partition, using 3 partitions for concurrency level 3 at the consumer side
	@Bean
    NewTopic topic()
	{
	  return TopicBuilder.name(registrationTopic)
			  .partitions(partitions)
					.build();
	}
	
	

}
