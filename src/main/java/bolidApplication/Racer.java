package bolidApplication;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class Racer {

    private final JmsTemplate jmsTemplate;
    private final JmsMessagingTemplate jmsMessagingTemplate;

    private final static Logger LOG = LoggerFactory.getLogger(Racer.class);

    public void sendPitStopRequest(){

        jmsMessagingTemplate.setJmsTemplate(jmsTemplate);

        PitStopResponseMessage responseMessage = jmsMessagingTemplate.convertSendAndReceive(
                JmsConfig.QUEUE_SEND_AND_RECEIVE,
                "Request for pitstop",
                PitStopResponseMessage.class
        );

        String responseText = responseMessage.getMessage();

        LOG.info("I've received a response: "+responseText);
    }
}
