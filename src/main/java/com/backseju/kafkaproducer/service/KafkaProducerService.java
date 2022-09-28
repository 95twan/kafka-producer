package com.backseju.kafkaproducer.service;

import com.backseju.kafkaproducer.entity.Assignment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "test";
    private final KafkaTemplate<String, Assignment> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Assignment> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Assignment assignment) {
        this.kafkaTemplate.send(TOPIC, assignment);
    }
}
