package com.microservices.eventdriven.aggregation.advice;

import com.microservices.eventdriven.aggregation.dto.ErrorResponseDTO;
import com.microservices.eventdriven.aggregation.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseDTO resourceNotFoundException(BadRequestException e) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO();
        errorResponse.setMessage("Service error. Please contact backend team with message-"+e.getMessage());
        return errorResponse;
    }
}
