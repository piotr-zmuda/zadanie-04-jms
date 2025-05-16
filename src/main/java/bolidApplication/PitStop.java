package bolidApplication;

import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

enum Status {
    OK,
    POTENTIAL_MALFUNCTION,
    MALFUNCTION

}

@Component
@RequiredArgsConstructor
public class PitStop {
    private final static Logger LOG = LoggerFactory.getLogger(PitStop.class);

    private final JmsTemplate jmsTemplate;


    @JmsListener(destination = JmsConfig.BOLID_STATUS, containerFactory = "queueConnectionFactory")
    public void receiverHelloWorld(@Payload BolidMessage statusMessage,
                                   MessageHeaders messageHeaders,
                                   Message message) {

        LOG.info("Received a message in PitStop: {}", statusMessage);

        LOG.info("Bolid status: "+ checkBolid(statusMessage));
    }

    private String checkBolid(BolidMessage bolidMessage){
        String response = "";

        response += getStatus("Oil status: ",checkOil(bolidMessage.getOilPressure()), bolidMessage) +"\n";
        response += getStatus("Tires status: ",checkTires(bolidMessage.getTyresPressure()), bolidMessage) +"\n";
        response += getStatus("Temperature status: ",checkTemp(bolidMessage.getTemp()), bolidMessage) +"\n";
        response += getStatus("Speed status: ",checkSpeed(bolidMessage.getSpeed()), bolidMessage) +"\n";

        return response;
    }

    private String getStatus(String category, Status status, BolidMessage bolidMessage){
        if( status == Status.MALFUNCTION){
            jmsTemplate.convertAndSend(JmsConfig.BOLID_MALFUNCTION, bolidMessage);
            LOG.info("Sent request to driver");
            jmsTemplate.convertAndSend(JmsConfig.BOLID_POTENTIAL_MALFUNCTION, bolidMessage);
            LOG.info("Sent warning to mechanics");

            StatusSender.getBolid().setBolidStatus(Status.MALFUNCTION);
        }else if( status == Status.POTENTIAL_MALFUNCTION){
            if(!StatusSender.getBolid().getBolidStatus().equals(Status.MALFUNCTION)){
                StatusSender.getBolid().setBolidStatus(Status.POTENTIAL_MALFUNCTION);
            }
            jmsTemplate.convertAndSend(JmsConfig.BOLID_POTENTIAL_MALFUNCTION, bolidMessage);
            LOG.info("Sent warning to mechanics");
        }

        return category + status.toString();
    }
    private Status checkOil(long oilPressure){
        if(oilPressure > 65)
            return Status.MALFUNCTION;
        if(oilPressure > 55)
            return Status.MALFUNCTION;
        return Status.OK;
    }

    private Status checkTires(int [] tiresPreesure){
        for(int tire: tiresPreesure){
            if(tire < 150){
                return Status.MALFUNCTION;
            }
            if(tire < 250){
                return Status.POTENTIAL_MALFUNCTION;
            }
        }

        return Status.OK;
    }

    private Status checkTemp(long temp){
        if (temp > 120)
            return Status.MALFUNCTION;
        if(temp > 110)
            return Status.POTENTIAL_MALFUNCTION;

        return Status.OK;
    }

    private Status checkSpeed(long speed){
        if(speed > 300)
            return Status.MALFUNCTION;
        if(speed > 250)
            return Status.POTENTIAL_MALFUNCTION;
        return Status.OK;
    }
}
