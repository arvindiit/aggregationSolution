package com.microservices.eventdriven.aggregation.backend.service;

import com.microservices.eventdriven.aggregation.backend.response.Response;
import com.microservices.eventdriven.aggregation.backend.response.TrackingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrackService extends Service<String> {

    @Value("${backend.service.endpoint.track}")
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public Response<String> formResponse(Map response) {
        TrackingResponse trackingResponse = new TrackingResponse();
        trackingResponse.setTrack(response);
        return trackingResponse;
    }
}
