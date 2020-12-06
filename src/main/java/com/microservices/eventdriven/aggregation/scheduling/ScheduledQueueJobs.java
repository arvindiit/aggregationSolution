package com.microservices.eventdriven.aggregation.scheduling;

import com.microservices.eventdriven.aggregation.backend.service.PricingService;
import com.microservices.eventdriven.aggregation.backend.service.ShipmentService;
import com.microservices.eventdriven.aggregation.backend.service.TrackService;
import com.microservices.eventdriven.aggregation.queue.QueueFactory;
import com.microservices.eventdriven.aggregation.queue.QueueMessage;
import com.microservices.eventdriven.aggregation.queue.QueueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Scheduler to run at specified time and to pick the queue message
 * from the queue with throttle limit.
 */
@Component
public class ScheduledQueueJobs {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private PricingService pricingService;
    private TrackService trackService;
    private ShipmentService shipmentService;

    @Value("${api.throttle.limit}")
    private int throttleLimit;

    public ScheduledQueueJobs(PricingService pricingService, TrackService trackService, ShipmentService shipmentService) {
        this.pricingService = pricingService;
        this.trackService = trackService;
        this.shipmentService = shipmentService;
    }

    @Scheduled(fixedRate = 200)
    public void schedulePricingService() {
        Queue<QueueMessage> queue = QueueFactory.getQueueDetails(QueueType.PRICING);
        assert queue != null;
        List<QueueMessage> list = getQueueMessagesList(queue);
        if(list.size() != 0) {
            logger.debug("picked up pricing job");
            pricingService.executeMessage(list);
        }
    }

    @Scheduled(fixedRate = 100)
    public void scheduleTrackService() {
        Queue<QueueMessage> queue = QueueFactory.getQueueDetails(QueueType.TRACK);
        assert queue != null;
        List<QueueMessage> list = getQueueMessagesList(queue);
        if(list.size() != 0) {
            logger.debug("picked up track job");
            trackService.executeMessage(list);
        }
    }
    @Scheduled(fixedRate = 300)
    public void scheduleShipmentService() {
        Queue<QueueMessage> queue = QueueFactory.getQueueDetails(QueueType.SHIPMENTS);
        assert queue != null;
        List<QueueMessage> list = getQueueMessagesList(queue);
        if(list.size() != 0) {
            logger.debug("picked up shipments job");
            shipmentService.executeMessage(list);
        }
    }

    private  List<QueueMessage> getQueueMessagesList(Queue<QueueMessage> queue){
        int size = queue.size();
        List<QueueMessage> list = new ArrayList<>();
        if(size >= throttleLimit) {
            for (int i = 0; i < throttleLimit; i++) {
                list.add(queue.poll());
            }
        }
        return list;
    }
}
