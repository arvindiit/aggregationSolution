package com.microservices.eventdriven.aggregation.task;

import com.microservices.eventdriven.aggregation.queue.QueueFactory;
import com.microservices.eventdriven.aggregation.queue.QueueMessage;
import com.microservices.eventdriven.aggregation.queue.QueueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * Task to publish the message to a queue.
 * This wait for completion of all QueueMessage has
 * response ready or maximum of sleepTime X retryCount(8 sec)
 */

public class PublisherTask extends RecursiveTask<TaskResult> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private QueueType type;
    private List<QueueMessage> queueMessages;
    private int retryCount; // number of times task should try to fetch the result
    private int sleepTime; //in ms.
    public PublisherTask(Set<String> requestIds, QueueType type) {
        List<String> ids = requestIds.stream().filter( r -> ! r.isEmpty()).collect(Collectors.toList());
        queueMessages = new ArrayList<>();
        for (String id : ids) {
            queueMessages.add(new QueueMessage(id));
        }
        this.type = type;
        retryCount = 20;
        sleepTime = 400;

    }

    @Override
    protected TaskResult compute() {
        Queue<QueueMessage> queue = QueueFactory.getQueueDetails(this.type);
        assert queue != null;
        queue.addAll(this.queueMessages);
        logger.debug("Published to all the queues at-"+ LocalDateTime.now());
        waitForCompletion();
        TaskResult taskResult = new TaskResult();
        for (QueueMessage queueMessage : queueMessages){
            taskResult.add(queueMessage);
        }
        return taskResult;
    }

    private void waitForCompletion(){
        boolean allComplete = false;
        while(!allComplete && retryCount != 0){
           allComplete = true;
           for (QueueMessage queueMessage : queueMessages){
               if(!queueMessage.isResponseCompleted()){
                   allComplete = false;
                   break;
               }
           }
           try {
               retryCount--;
               Thread.sleep(sleepTime);
           } catch (InterruptedException e) {
              throw new RuntimeException("Service Error at backend");
           }
       }
        logger.debug("Task - "+this.type.name()+" finished  at-"+ LocalDateTime.now());
    }
}
