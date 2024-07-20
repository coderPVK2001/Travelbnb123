package com.TravelPractise6.TravelPractise6.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SmsServiceSIB {

        @Value("${sendinblue.api.key}")
        private String apiKey;

        private final RestTemplate restTemplate = new RestTemplate();

        public String sendSms(String toNumber, String message) {
            String url = "https://api.sendinblue.com/v3/transactionalSMS/sms";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            String senderr="prajwal";
            Map<String, Object> body = new HashMap<>();
            body.put("sender",senderr);
            body.put("recipient", toNumber);
            body.put("content", message);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            try {
                restTemplate.postForEntity(url, entity, String.class);
                return "Message sent successfully.";
            } catch (Exception e) {
                e.printStackTrace();
                return "Message failed with error: " + e.getMessage();
            }
        }
    }

