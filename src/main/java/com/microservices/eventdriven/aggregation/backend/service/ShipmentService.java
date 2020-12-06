package com.microservices.eventdriven.aggregation.backend.service;

import com.microservices.eventdriven.aggregation.backend.response.Response;
import com.microservices.eventdriven.aggregation.backend.response.ShipmentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ShipmentService extends Service<List<String>> {

    @Value("${backend.service.endpoint.shipments}")
    private String endpoint;

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public Response<List<String>> formResponse(Map response) {
        ShipmentResponse shipmentResponse = new ShipmentResponse();
        shipmentResponse.setShipments(response);
        return shipmentResponse;
    }
}
