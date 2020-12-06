package com.microservices.eventdriven.aggregation.backend.service;

import com.microservices.eventdriven.aggregation.backend.response.Response;
import com.microservices.eventdriven.aggregation.queue.QueueMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Service<T> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${backend.service.host}")
    String host;

    @Value("${backend.service.port}")
    String port;

    RestTemplate restTemplate;
    public Service() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(5000);
        restTemplate = new RestTemplate(clientHttpRequestFactory);
    }

    public void executeMessage(List<QueueMessage> queueMessages) {
        List<String> ids = queueMessages.stream().map( r -> r.getRequestId()).collect(Collectors.toList());
        Response<T> response = execute(ids);
        queueMessages.forEach(r -> r.completeRequest(response.getData().get(r.getRequestId())));
        logger.debug("Finished backend service at-"+ LocalDateTime.now());

    }
    public void executeMessage(QueueMessage queueMessage) {
        List<QueueMessage> queueMessages = new ArrayList<>();
        queueMessages.add(queueMessage);
        executeMessage(queueMessages);
    }

    public Response<T> execute(List<String> ids){
        URI targetUrl= UriComponentsBuilder.fromUriString("http://"+host+":"+port+"/")
                .path(getEndpoint())
                .queryParam("q", ids)
                .build()
                .encode()
                .toUri();
        Map<String, Object> response = restTemplate.getForObject(targetUrl, HashMap.class);
        return formResponse(response);
    }

    public abstract String getEndpoint();

    public abstract Response<T> formResponse(Map response);
}
