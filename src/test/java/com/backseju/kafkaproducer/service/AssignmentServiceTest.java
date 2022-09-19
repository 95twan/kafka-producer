package com.backseju.kafkaproducer.service;

import com.backseju.kafkaproducer.entity.Assignment;
import com.backseju.kafkaproducer.repository.AssignmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {
    @Mock
    private AssignmentRepository assignmentRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private AssignmentService assignmentService;

    @Test
    void saveAssignmentFile() throws IOException {
        // give
        String fileName = "dummy.zip";
        FileInputStream fileInputStream = new FileInputStream("/Users/twan/Desktop/project-oj/" + fileName);
        MockMultipartFile file = new MockMultipartFile("assignment", fileName, MediaType.MULTIPART_FORM_DATA_VALUE, fileInputStream);

        Long assignmentId = 1L;
        given(assignmentRepository.save(any())).willReturn(
                Assignment.builder().id(assignmentId).build()
        );

        // when
        assignmentService.saveAssignmentFile(file);

        // then
        File savedFile = new File(fileName);
        assertThat(savedFile).isFile();
        savedFile.delete();

        then(assignmentRepository).should().save(any());
        then(kafkaProducerService).should().sendMessage(String.valueOf(assignmentId));
    }
}
