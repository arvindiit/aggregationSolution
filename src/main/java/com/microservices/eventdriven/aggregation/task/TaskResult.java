package com.microservices.eventdriven.aggregation.task;

import com.microservices.eventdriven.aggregation.queue.QueueMessage;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class TaskResult {
   Map<String, Object> result;

    public TaskResult() {
        result = new HashMap<>();
    }
    public void add(QueueMessage queueMessage) {
        result.put(queueMessage.getRequestId(), queueMessage.getResponse());
    }
}
