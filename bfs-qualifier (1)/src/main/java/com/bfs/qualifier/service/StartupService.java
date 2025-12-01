package com.bfs.qualifier.service;

import com.bfs.qualifier.model.WebhookResponse;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartupService {

    private final WebhookService webhookService;
    private final SqlSolverService sqlSolverService;

    @PostConstruct
    public void runOnStart() {

        System.out.println("➡ Generating Webhook...");
        WebhookResponse res = webhookService.callGenerateWebhook();

        String webhookUrl = res.getWebhookUrl();
        String accessToken = res.getAccessToken();

        int lastDigits = 61;

        String sql = sqlSolverService.getSolution(lastDigits);

        System.out.println("➡ Submitting SQL...");
        webhookService.submitFinalQuery(webhookUrl, accessToken, sql);

        System.out.println("✔ Submission completed!");
    }
}
