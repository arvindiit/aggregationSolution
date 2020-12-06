package com.microservices.eventdriven.aggregation.pool;

import java.util.concurrent.ForkJoinPool;

public class CustomThreadPool {

    private CustomThreadPool() {
    }

    private static class CustomThreadPoolSingleton {
        private static final ForkJoinPool INSTANCE = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
    }

    public static ForkJoinPool getPool()
    {
        return CustomThreadPoolSingleton.INSTANCE;
    }

}
