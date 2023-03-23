package com.montymobile.consumerfcm.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;

@Configuration
public class FCMConfig {
	
	@Value("${firebase.credentials.file}")
	private String firebaseCredentialsResource;
	
	@Bean
	FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
	    return FirebaseMessaging.getInstance(firebaseApp);
	}
	
	@Bean
	FirebaseApp firebaseApp(GoogleCredentials credentials) {
	    FirebaseOptions options = FirebaseOptions.builder()
	      .setCredentials(credentials)
	      .build();

	    return FirebaseApp.initializeApp(options);
	}
	
	@Bean
	GoogleCredentials googleCredentials() throws IOException {
	    if (firebaseCredentialsResource != null) {
	        try (InputStream is = new ClassPathResource(firebaseCredentialsResource).getInputStream()) {
	            return GoogleCredentials.fromStream(is);
	        }
	    } 
	    else {
	        //Useful when running inside GKE
	        return GoogleCredentials.getApplicationDefault();
	    }
	}
	
	

}
