package com.backseju.kafkaproducer.service;

import com.backseju.kafkaproducer.entity.Assignment;
import com.backseju.kafkaproducer.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    private final KafkaProducerService kafkaProducerService;

    @Value("${assignment.dir}")
    private String fileDir;

    public void saveAssignmentFile(MultipartFile assignmentFile) {
        try {
            // TODO: 2022/09/19 파일 네이밍 고민
            File uploadFile = new File(fileDir + assignmentFile.getOriginalFilename());
            assignmentFile.transferTo(uploadFile);
            Assignment assignment = assignmentRepository.save(Assignment.builder().uploadUrl(uploadFile.getCanonicalPath()).build());

            kafkaProducerService.sendMessage(assignment.getId());
        } catch (IOException e) {
            // TODO: 2022/09/19 파일 저장시 에러처리
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }

    public Resource getAssignmentFile(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(EntityNotFoundException::new);
        try {
            Path path = Paths.get(assignment.getUploadUrl());
            return new InputStreamResource(Files.newInputStream(path));
        } catch (IOException e) {
            // TODO: 2022/09/19 파일 전송시 에러처리
            System.out.println(e.getMessage());
            throw new RuntimeException();
        }
    }
}
