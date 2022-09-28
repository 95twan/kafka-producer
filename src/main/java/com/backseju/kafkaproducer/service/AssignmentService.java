package com.backseju.kafkaproducer.service;

import com.backseju.kafkaproducer.entity.Assignment;
import com.backseju.kafkaproducer.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final KafkaProducerService kafkaProducerService;


    public void saveAssignment(String assignmentUrl) {
        Assignment assignment = assignmentRepository.save(Assignment.builder().uploadUrl(assignmentUrl).build());

        kafkaProducerService.sendMessage(assignment);
    }
}
