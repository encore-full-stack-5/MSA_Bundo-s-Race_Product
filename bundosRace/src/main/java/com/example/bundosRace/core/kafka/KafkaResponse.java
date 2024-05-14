package com.example.bundosRace.core.kafka;

public record KafkaResponse<T>(
        T data,
        String status
) {
}
