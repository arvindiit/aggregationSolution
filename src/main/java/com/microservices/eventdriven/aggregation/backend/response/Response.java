package com.microservices.eventdriven.aggregation.backend.response;

import java.util.Map;

public abstract class Response<T> {
    public abstract Map<String, T> getData();
}
