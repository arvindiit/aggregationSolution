package com.microservices.eventdriven.aggregation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    Map<String, Double> pricing;
    Map<String, List<String>> shipments;
    Map<String, String> track;

    public void addPricing(Map<String, Object> map){
        pricing = pricing != null ? pricing : new HashMap<>();
        for (String key : map.keySet()) pricing.put(key, (Double) map.get(key));
    }

    public void addTrack(Map<String, Object> map){
        track = track != null ? track : new HashMap<>();
        for (String key : map.keySet()) track.put(key, (String) map.get(key));
    }

    public void addShipments(Map<String, Object> map){
        shipments = shipments != null ? shipments : new HashMap<>();
        for (String key : map.keySet()) shipments.put(key, (List<String>) map.get(key));
    }

}
