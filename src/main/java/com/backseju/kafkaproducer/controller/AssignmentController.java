package com.backseju.kafkaproducer.controller;

import com.backseju.kafkaproducer.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/file")
    @ResponseStatus(HttpStatus.CREATED)
    public String uploadAssignmentFile(@RequestParam(name = "assignment") MultipartFile assignment) {

        assignmentService.saveAssignmentFile(assignment);

        return "success";
    }

    @GetMapping("/file/{assignmentId}")
    public ResponseEntity<Resource> getAssignmentFile(@PathVariable Long assignmentId) {

        Resource resource = assignmentService.getAssignmentFile(assignmentId);

        return ResponseEntity.ok().body(resource);
    }
}
