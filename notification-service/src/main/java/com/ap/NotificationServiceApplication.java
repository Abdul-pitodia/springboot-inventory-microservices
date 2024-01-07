package com.ap;

import com.ap.config.EmailConfig;
import com.ap.event.OrderPlacedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailConfig emailConfig;

    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }

    @KafkaListener(topics = "order-placed-topic")
    public void handleOrderPlacedEvent(OrderPlacedEvent orderPlacedEvent){
        log.info("Event received {}", orderPlacedEvent.getOrderNumber() + " order number placed.");
        sendEmail(orderPlacedEvent);
    }

    public void sendEmail(OrderPlacedEvent orderPlacedEvent){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(orderPlacedEvent.getEmail());
        message.setText("Order placed , order Id is: " + orderPlacedEvent.getOrderNumber());
        message.setSubject("Order confirmation");
        message.setFrom(emailConfig.getSenderEmail());
        javaMailSender.send(message);
        log.info("Sent email");
    }
}
