package com.microservices.eventdriven.aggregation.scheduling;

import com.microservices.eventdriven.aggregation.queue.QueueFactory;
import com.microservices.eventdriven.aggregation.queue.QueueMessage;
import com.microservices.eventdriven.aggregation.backend.service.PricingService;
import com.microservices.eventdriven.aggregation.backend.service.ShipmentService;
import com.microservices.eventdriven.aggregation.backend.service.TrackService;
import com.microservices.eventdriven.aggregation.queue.QueueType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Queue;

/**
 * scheduler to pick up old jobs which are more than x sec old
 * timeLimit = x sec
 */
@Component
public class ScheduleOldQueueJobs {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private PricingService pricingService;
    private TrackService trackService;
    private ShipmentService shipmentService;

    @Value("${old.jobs.processing.time.limit.in_seconds}")
    private int timeLimit;

    public ScheduleOldQueueJobs(PricingService pricingService, TrackService trackService, ShipmentService shipmentService) {
        this.pricingService = pricingService;
        this.trackService = trackService;
        this.shipmentService = shipmentService;
    }

    @Scheduled(fixedRate = 115)
    public void schedulePricingService() {
        Queue<QueueMessage> queue = QueueFactory.getQueueDetails(QueueType.PRICING);
        Iterator<QueueMessage> it = queue.iterator();
        while(it.hasNext()){
            QueueMessage queueMessage = it.next();
            if(queueMessage.getDateTime().plusSeconds(timeLimit).isBefore(LocalDateTime.now())){
                logger.debug("picked up queue message for pricing-"+queueMessage.getRequestId());
                queue.remove(queueMessage);
                pricingService.executeMessage(queueMessage);
            }
        }
    }

    @Scheduled(fixedRate = 110)
    public void scheduleTrackService() {
        Queue<QueueMessage> queue = QueueFactory.getQueueDetails(QueueType.TRACK);
        Iterator<QueueMessage> it = queue.iterator();
        while(it.hasNext()){
            QueueMessage queueMessage = it.next();
            if(queueMessage.getDateTime().plusSeconds(timeLimit).isBefore(LocalDateTime.now())){
                logger.debug("picked up queue message for track-"+queueMessage.getRequestId());
                queue.remove(queueMessage);
                trackService.executeMessage(queueMessage);
            }
        }
    }

    @Scheduled(fixedRate = 100)
    public void scheduleShipmentService() {
        Queue<QueueMessage> queue = QueueFactory.getQueueDetails(QueueType.SHIPMENTS);
        Iterator<QueueMessage> it = queue.iterator();
        while(it.hasNext()){
            QueueMessage queueMessage = it.next();
            if(queueMessage.getDateTime().plusSeconds(timeLimit).isBefore(LocalDateTime.now())){
                logger.debug("picked up queue message for shipment-"+queueMessage.getRequestId());
                queue.remove(queueMessage);
                shipmentService.executeMessage(queueMessage);
            }
        }
    }
}
