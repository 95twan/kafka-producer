package com.backseju.kafkaproducer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "test";
    private final KafkaTemplate<String, Long> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Long> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Long message) {
        this.kafkaTemplate.send(TOPIC, message);
    }
}
