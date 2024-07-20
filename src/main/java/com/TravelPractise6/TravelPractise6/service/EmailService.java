package com.TravelPractise6.TravelPractise6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    JavaMailSender js;

    public void sendemail( String email ,String subject,String message) {

        SimpleMailMessage smm= new SimpleMailMessage();
        smm.setFrom("prajwalvkande2001@outlook.com");
        smm.setTo(email);
        smm.setSubject(subject);
        smm.setText(message);

        js.send(smm);

    }
}
