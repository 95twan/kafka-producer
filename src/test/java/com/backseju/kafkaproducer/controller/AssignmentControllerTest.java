package com.backseju.kafkaproducer.controller;

import com.backseju.kafkaproducer.service.AssignmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileInputStream;

import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AssignmentController.class)
class AssignmentControllerTest {

    private final MockMvc mvc;

    @MockBean
    private AssignmentService assignmentService;

    public AssignmentControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @Test
    void uploadAssignmentFile() throws Exception {
        // given
        String fileName = "dummy.zip";
        FileInputStream fileInputStream = new FileInputStream("/Users/twan/Desktop/project-oj/" + fileName);
        MockMultipartFile file = new MockMultipartFile("assignment", fileName, MediaType.MULTIPART_FORM_DATA_VALUE, fileInputStream);

        // when && then
        mvc.perform(multipart("/api/assignments/file")
                        .file(file)
                )
                .andExpect(status().isCreated())
                .andExpect(content().string("success"))
        ;

        then(assignmentService).should().saveAssignmentFile(file);
    }
}
