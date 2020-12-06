package com.microservices.eventdriven.aggregation.backend.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class PricingResponse extends Response<Double> {
    Map<String, Double> pricing;

    @Override
    public Map<String, Double> getData() {
        return pricing;
    }
}
