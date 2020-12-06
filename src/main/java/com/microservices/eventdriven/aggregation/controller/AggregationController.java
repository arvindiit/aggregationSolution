package com.microservices.eventdriven.aggregation.controller;

import com.microservices.eventdriven.aggregation.dto.RequestDTO;
import com.microservices.eventdriven.aggregation.dto.ResponseDTO;
import com.microservices.eventdriven.aggregation.pool.CustomThreadPool;
import com.microservices.eventdriven.aggregation.service.QueuePublisherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/aggregation")
@CrossOrigin(origins = "*")
public class AggregationController {



    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO getConfigurations(@RequestParam(required = false) Set<String> pricing,
                                         @RequestParam(required = false) Set<String> track,
                                         @RequestParam(required = false) Set<String> shipments) {


        RequestDTO requestDTO = new RequestDTO(pricing, track, shipments);
        return CustomThreadPool.getPool().invoke(new QueuePublisherService(requestDTO));
    }
}
