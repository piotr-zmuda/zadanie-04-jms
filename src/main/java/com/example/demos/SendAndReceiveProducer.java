package com.example.demos;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SendAndReceiveProducer {

    private final JmsTemplate jmsTemplate;
    private final JmsMessagingTemplate jmsMessagingTemplate;

    private final static Logger LOG = LoggerFactory.getLogger(SendAndReceiveProducer.class);

    @Scheduled(fixedRate = 2000)
    public void sendAndReceive(){
        HelloMessage message = HelloMessage.builder()
                .id(HelloMessage.nextId())
                .createdAt(LocalDateTime.now())
                .message("Thank you!")
                .build();

        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);


        LOG.info("I'm about to send a message");

        ResponseMessage responseMessage = jmsMessagingTemplate.convertSendAndReceive(
                JmsConfig.QUEUE_SEND_AND_RECEIVE,
                message,
                ResponseMessage.class
        );

        String responseText = responseMessage.getMessage();

        LOG.info("I've received a response: "+responseText + "\tconvertedMessage: "+responseMessage);
    }
}
