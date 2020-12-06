package com.microservices.eventdriven.aggregation.backend.service;

import com.microservices.eventdriven.aggregation.backend.response.PricingResponse;
import com.microservices.eventdriven.aggregation.backend.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PricingService extends Service<Double> {

    @Value("${backend.service.endpoint.pricing}")
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public Response<Double> formResponse(Map response) {
        PricingResponse pricingResponse = new PricingResponse();
        pricingResponse.setPricing(response);
        return pricingResponse;
    }
}
