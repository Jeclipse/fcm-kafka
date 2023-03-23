package com.montymobile.consumerfcm.service;

import java.util.List;

public interface FCMService<T> {
	
	 void sendToFCM(List<T> messages);

}
