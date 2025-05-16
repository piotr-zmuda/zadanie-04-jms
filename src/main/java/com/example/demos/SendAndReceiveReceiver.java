package com.example.demos;

import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.Local;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SendAndReceiveReceiver {

    private final JmsTemplate jmsTemplate;
    private final static Logger LOG = LoggerFactory.getLogger(SendAndReceiveProducer.class);


    @JmsListener(destination = JmsConfig.QUEUE_SEND_AND_RECEIVE)
    public void receiveAndRespond(@Payload HelloMessage convertedMessage,
                                  Message message) throws JMSException {
        LOG.info("Received a message" + convertedMessage);

        ResponseMessage responseMessage = ResponseMessage.builder()
                .id(ResponseMessage.nextId())
                .correlatedMessageId(convertedMessage.getId())
                .createdAt(LocalDateTime.now())
                .message("You're welcome.")
                .build();

        Destination replyTo = message.getJMSReplyTo();

        jmsTemplate.convertAndSend(replyTo, responseMessage);
    }
}
