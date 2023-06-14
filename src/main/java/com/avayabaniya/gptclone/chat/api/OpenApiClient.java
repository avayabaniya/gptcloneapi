package com.avayabaniya.gptclone.chat.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OpenApiClient {

    public String makeRequest(String jsonRequest, String url, String openApiToken) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + openApiToken);
            headers.set("Content-Type", "application/json");
            HttpEntity<String> request = new HttpEntity<>(jsonRequest, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Problem querying " + url + ". " +
                    " and error message " + e.getMessage());

            System.err.println(e.getLocalizedMessage());
            return null;
        }
    }
}
