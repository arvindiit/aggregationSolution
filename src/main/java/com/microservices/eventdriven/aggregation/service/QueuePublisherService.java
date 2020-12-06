package com.microservices.eventdriven.aggregation.service;

import com.microservices.eventdriven.aggregation.dto.RequestDTO;
import com.microservices.eventdriven.aggregation.dto.ResponseDTO;
import com.microservices.eventdriven.aggregation.queue.QueueType;
import com.microservices.eventdriven.aggregation.task.PublisherTask;
import com.microservices.eventdriven.aggregation.task.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.concurrent.RecursiveTask;

/**
 * Service to publish the message to respective queue.
 * Publishing the message to different queue is performed on different task,
 * hence processing on different queue will be in parallel.
 */

public class QueuePublisherService extends RecursiveTask<ResponseDTO> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    private RequestDTO requestDTO;

    public QueuePublisherService(RequestDTO requestDTO) {
        this.requestDTO = requestDTO;
    }

    @Override
    protected ResponseDTO compute() {
        return publish(requestDTO);
    }

    private ResponseDTO publish(RequestDTO requestDTO){
        ResponseDTO responseDTO = new ResponseDTO();
        PublisherTask publisherTaskPricing = null;
        PublisherTask publisherTaskTrack = null;
        PublisherTask publisherTaskShipments = null;

        if(requestDTO.getPricingIds() != null && ! requestDTO.getPricingIds().isEmpty()) {
            publisherTaskPricing = new PublisherTask(requestDTO.getPricingIds(), QueueType.PRICING);
            publisherTaskPricing.fork();
        }
        if(requestDTO.getTrackIds() != null && ! requestDTO.getTrackIds().isEmpty()) {
            publisherTaskTrack = new PublisherTask(requestDTO.getTrackIds(), QueueType.TRACK);
            publisherTaskTrack.fork();
        }
        if(requestDTO.getShipmentIds() != null && ! requestDTO.getShipmentIds().isEmpty()) {
            publisherTaskShipments = new PublisherTask(requestDTO.getShipmentIds(), QueueType.SHIPMENTS);
            publisherTaskShipments.fork();
        }

        if(publisherTaskPricing != null){
            TaskResult result = publisherTaskPricing.join();
            responseDTO.addPricing(result.getResult());
        }
        if(publisherTaskTrack != null){
            TaskResult result =  publisherTaskTrack.join();
            responseDTO.addTrack(result.getResult());
        }

        if(publisherTaskShipments != null){
            TaskResult result = publisherTaskShipments.join();
            responseDTO.addShipments(result.getResult());
        }
        logger.debug("Returning the response to consumer at-"+ LocalDateTime.now());

        return responseDTO;
    }
}
