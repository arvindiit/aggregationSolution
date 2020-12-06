package com.microservices.eventdriven.aggregation.backend.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ShipmentResponse extends Response<List<String>> {

    Map<String, List<String>> shipments;

    @Override
    public Map<String, List<String>> getData() {
        return shipments;
    }
}
