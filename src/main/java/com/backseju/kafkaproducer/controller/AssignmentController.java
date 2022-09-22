package com.backseju.kafkaproducer.controller;

import com.backseju.kafkaproducer.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAssignmentFile(@RequestParam(name = "assignmentUrl") String assignmentUrl) {

        assignmentService.saveAssignment(assignmentUrl);

        return "success";
    }
}
