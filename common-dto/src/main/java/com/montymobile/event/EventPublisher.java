package com.montymobile.event;

public interface EventPublisher<T> {
    void publishMessage(T message);
}