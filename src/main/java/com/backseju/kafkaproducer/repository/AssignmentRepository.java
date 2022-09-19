package com.backseju.kafkaproducer.repository;

import com.backseju.kafkaproducer.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
