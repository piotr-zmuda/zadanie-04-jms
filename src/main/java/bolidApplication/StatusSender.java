package bolidApplication;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static bolidApplication.BolidMessage.nextId;

@Component
public class StatusSender {
    @Autowired
    private final JmsTemplate jmsTemplate;
    @Autowired
    private final JmsMessagingTemplate jmsMessagingTemplate;

    @Getter
    private static final Bolid bolid = new Bolid();

    private static Racer racer;

    private final static Logger LOG = LoggerFactory.getLogger(StatusSender.class);

    public StatusSender(JmsTemplate jmsTemplate, JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.jmsMessagingTemplate = jmsMessagingTemplate;

        racer = new Racer(jmsTemplate, jmsMessagingTemplate);
    }

    @Scheduled(fixedRate = 1000)
    public void sendStatus() {
        BolidMessage message = BolidMessage.builder().id(nextId()).bolidId(bolid.getBolidIndex()).bolidStatus(bolid.getBolidStatus()).speed(bolid.getSpeed()).temp(bolid.getTemp()).tyresPressure(bolid.getTyresPressure()).oilPressure(bolid.getOilPressure()).createdAt(LocalDateTime.now()).lapNb(bolid.getLapNb()).bestLap(bolid.getBestLap()).racePlaceNb(bolid.getRacePlaceNb()).message(bolid.getStatus()).build();
        jmsTemplate.convertAndSend(JmsConfig.BOLID_STATUS, message);
        if(bolid.getBolidStatus() != Status.OK){
            racer.sendPitStopRequest();
        }
        LOG.info("Sent status:" + message);

    }

}
