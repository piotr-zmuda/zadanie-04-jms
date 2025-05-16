package bolidApplication;

import com.example.demos.JmsConfig;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Manager {

    private final JmsTemplate jmsTemplate;
    private final static Logger LOG = LoggerFactory.getLogger(Manager.class);


    @JmsListener(destination = JmsConfig.QUEUE_SEND_AND_RECEIVE)
    public void receiveAndRespond(@Payload String convertedMessage,
                                  Message message) throws JMSException {
        LOG.info("Received a request for pitstop" + convertedMessage);

        boolean answer = Math.random() < 0.5;

        PitStopResponseMessage responseMessage = PitStopResponseMessage.builder()
                .answer(answer)
                .message("The answer for pit-stop request is "+ answer)
                .build();


        Destination replyTo = message.getJMSReplyTo();

        jmsTemplate.convertAndSend(replyTo, responseMessage);
    }
}

