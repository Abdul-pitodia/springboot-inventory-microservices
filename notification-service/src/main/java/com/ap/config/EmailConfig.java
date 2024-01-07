package com.ap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailConfig {

    public String getSenderEmail() {
        return senderEmail;
    }

    private final String senderEmail;

    @Autowired
    public EmailConfig(@Value("${spring.mail.username}") String senderEmail){
        this.senderEmail = senderEmail;
    }


}
