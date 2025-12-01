package com.bfs.qualifier.model;
import lombok.Data;

@Data
public class WebhookResponse {
    private String webhookUrl;
    private String accessToken;
}
