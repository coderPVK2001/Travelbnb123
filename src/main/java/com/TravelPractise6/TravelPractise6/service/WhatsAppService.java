package com.TravelPractise6.TravelPractise6.service;

import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.twilio.type.PhoneNumber;

@Service
public class WhatsAppService {

    @Value("${twilio.trial-number}")
    private String fromNumber;


    public void sendMessage(String to, String message) {
        Message.creator(
                new PhoneNumber("whatsapp:" + to),
                new PhoneNumber("whatsapp:" + fromNumber),
                message
        ).create();
    }
}

