package com.bfs.qualifier.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bfs.qualifier.model.SubmitRequest;
import com.bfs.qualifier.model.WebhookResponse;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final RestTemplate restTemplate;

    public WebhookResponse callGenerateWebhook() {

        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("name", "Roushan Raj");
        body.put("regNo", "22BCE7661");
        body.put("email", "kumarjharoushan3@gmail.com");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        WebhookResponse response = restTemplate.postForObject(url, entity, WebhookResponse.class);

        System.out.println("Webhook URL = " + response.getWebhookUrl());
        System.out.println("Access Token = " + response.getAccessToken());

        return response;
    }

    public void submitFinalQuery(String webhookUrl, String token, String sql) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        SubmitRequest request = new SubmitRequest(sql);
        HttpEntity<SubmitRequest> entity = new HttpEntity<>(request, headers);

        restTemplate.exchange(webhookUrl, HttpMethod.POST, entity, String.class);
    }
}
