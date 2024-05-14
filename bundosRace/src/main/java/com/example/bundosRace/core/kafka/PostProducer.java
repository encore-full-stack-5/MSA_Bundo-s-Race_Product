package com.example.bundosRace.core.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostProducer {
    private final KafkaTemplate<String, KafkaResponse<String>> kafkaTemplate;
    private final KafkaTemplate<String, KafkaDto> kafkaDToTemplate;

    @Bean
    public NewTopic postTopic() {
        return new NewTopic("test-topic", 1, (short) 1);
    }

    public void send(KafkaResponse response, String status) {
        kafkaDToTemplate.send("test-topic", new KafkaDto(1L,"test","test"));
    }

}