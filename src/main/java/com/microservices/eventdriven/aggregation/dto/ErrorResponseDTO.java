package com.microservices.eventdriven.aggregation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO extends ResponseDTO {
    private String message;
}
