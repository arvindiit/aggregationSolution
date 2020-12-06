package com.microservices.eventdriven.aggregation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RequestDTO {
    private Set<String> pricingIds;
    private Set<String> trackIds;
    private Set<String> shipmentIds;

    public RequestDTO(Set<String> pricingIds,
                      Set<String> trackIds,
                      Set<String> shipmentIds) {
       this.pricingIds = pricingIds;
       this.trackIds = trackIds;
       this.shipmentIds = shipmentIds;
    }
}
