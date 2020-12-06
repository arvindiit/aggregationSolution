package com.microservices.eventdriven.aggregation.backend.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class TrackingResponse extends Response<String> {
    Map<String, String> track;

    @Override
    public Map<String, String> getData() {
        return track;
    }
}
