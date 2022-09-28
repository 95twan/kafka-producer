package com.backseju.kafkaproducer.service;

import com.backseju.kafkaproducer.entity.Assignment;
import com.backseju.kafkaproducer.repository.AssignmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

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
    void saveAssignment() throws IOException {
        // give
//        String fileName = "dummy.zip";
//        FileInputStream fileInputStream = new FileInputStream("/Users/twan/Desktop/project-oj/" + fileName);
//        MockMultipartFile file = new MockMultipartFile("assignment", fileName, MediaType.MULTIPART_FORM_DATA_VALUE, fileInputStream);
        String assignmentUrl = "/Users/twan/Desktop/project-oj/upload/dummy.zip";

        Long assignmentId = 1L;
        given(assignmentRepository.save(any())).willReturn(
                Assignment.builder().id(assignmentId).uploadUrl(assignmentUrl).build()
        );

        // when
        assignmentService.saveAssignment(assignmentUrl);

        // then
//        File savedFile = new File(fileName);
//        assertThat(savedFile).isFile();
//        savedFile.delete();

        then(assignmentRepository).should().save(any());
        then(kafkaProducerService).should().sendMessage(any());
    }
}
