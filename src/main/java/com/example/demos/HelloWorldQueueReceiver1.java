package com.example.demos;

import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldQueueReceiver1 {

    private final static Logger LOG = LoggerFactory.getLogger(HelloWorldQueueReceiver1.class);

    @JmsListener(destination = JmsConfig.QUEUE_HELLO_WORLD, containerFactory = "queueConnectionFactory")
    public void receiverHelloWorld(@Payload HelloMessage convertedMessage,
                                   MessageHeaders messageHeaders,
                                   Message message) {
        LOG.info("Received a message: {}", convertedMessage);
    }
}
