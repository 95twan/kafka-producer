package com.backseju.kafkaproducer.service;

import com.backseju.kafkaproducer.entity.Assignment;
import com.backseju.kafkaproducer.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final KafkaProducerService kafkaProducerService;

    public void saveAssignmentFile(MultipartFile assignmentFile) {
        try {
            // TODO: 2022/09/19 파일 네이밍 고민
            File uploadFile = new File(assignmentFile.getOriginalFilename());
            assignmentFile.transferTo(uploadFile);
            Assignment assignment = assignmentRepository.save(Assignment.builder().uploadUrl(uploadFile.getCanonicalPath()).build());

            kafkaProducerService.sendMessage(String.valueOf(assignment.getId()));
        } catch (IOException e) {
            // TODO: 2022/09/19 파일 저장시 에러처리
            System.out.println(e.getMessage());
        }
    }
}
