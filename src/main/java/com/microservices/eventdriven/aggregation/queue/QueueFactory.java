package com.microservices.eventdriven.aggregation.queue;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This is container class for all the queues.
 * It make sure that queue stays singleton
 */

public class QueueFactory {

    private QueueFactory() {
    }

    private static class PricingQueueSingleton {
        private static final Queue<QueueMessage> INSTANCE = new LinkedBlockingQueue<>();
    }

    private static class TrackingQueueSingleton {
        private static final  Queue<QueueMessage> INSTANCE = new LinkedBlockingQueue<>();
    }


    private static class ShipmentQueueSingleton {
        private static final Queue<QueueMessage> INSTANCE = new LinkedBlockingQueue<>();
    }

    public static Queue<QueueMessage> getQueueDetails(QueueType type){
        if(type.equals(QueueType.PRICING)) {
            return PricingQueueSingleton.INSTANCE;
        }
        if(type.equals(QueueType.TRACK)) {
            return TrackingQueueSingleton.INSTANCE;
        }
        if(type.equals(QueueType.SHIPMENTS)) {
            return ShipmentQueueSingleton.INSTANCE;
        }
        return null;
    }


}
