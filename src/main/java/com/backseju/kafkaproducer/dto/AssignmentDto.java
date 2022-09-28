package com.backseju.kafkaproducer.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AssignmentDto {
    private Long id;

    private String uploadUrl;
}
