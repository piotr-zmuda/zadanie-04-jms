package bolidApplication;

import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RaceResolver {
    private final static Logger LOG = LoggerFactory.getLogger(RaceResolver.class);

    @JmsListener(destination = JmsConfig.BOLID_STATUS, containerFactory = "queueConnectionFactory")
    public void receiverHelloWorld(@Payload BolidMessage statusMessage,
                                   MessageHeaders messageHeaders,
                                   Message message) {
        LOG.info("Race status: Best Lap:{}, LapNb:{}, RaceNb:{}", statusMessage.getBestLap(), statusMessage.getLapNb(), statusMessage.getRacePlaceNb());
    }




}
