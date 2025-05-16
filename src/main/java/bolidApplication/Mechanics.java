package bolidApplication;

import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Mechanics {
    private final static Logger LOG = LoggerFactory.getLogger(RaceResolver.class);

    @JmsListener(destination = JmsConfig.BOLID_MALFUNCTION, containerFactory = "queueConnectionFactory")
    public void receiverHelloWorld(@Payload BolidMessage statusMessage,
                                   MessageHeaders messageHeaders,
                                   Message message) {
        LOG.info("Mechanics received warning: {}", statusMessage.getMessage());
    }
}
