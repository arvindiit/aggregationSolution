package com.microservices.eventdriven.aggregation.queue;

import com.microservices.eventdriven.aggregation.exception.BadRequestException;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QueueMessage {
    private String requestId;
    private Object response;
    private boolean responseCompleted;
    private LocalDateTime dateTime;

    public QueueMessage(String requestId) {
        if(requestId.trim().isEmpty()){
            throw new BadRequestException("Please provide the ids");
        }
        this.requestId = requestId;
        this.dateTime = LocalDateTime.now();
        responseCompleted = false;
    }
    public void completeRequest(Object response){
        this.response = response;
        this.responseCompleted = true;
    }
}
