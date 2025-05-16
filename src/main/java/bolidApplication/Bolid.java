package bolidApplication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class Bolid {

    private static int boldsIndexes = 0;

    private Status bolidStatus = Status.OK;

    private int racePlaceNb;

    private long bestLap;

    private long lapNb;

    private int bolidIndex;
    public Bolid (){
        bolidIndex = boldsIndexes;
        boldsIndexes++;
    }

    private long speed = 0;

    private long temp = 100;

    private int [] tyresPressure = {355,355,355,355};

    private long oilPressure = 80;

    public void speedUp(){
        speed = speed + 10;
        oilPressure = oilPressure + 1;
    }

    public void slowDown(){
        speed = speed - 10;
        oilPressure = oilPressure - 1;
    }

    public void tirePuncture(int tyre){
        tyresPressure[tyre] = 0;
    }

    public String getStatus(){
        if(temp > 100){
            return "Overheating! temperature:"+temp;
        }
        StringBuilder tmpPress = new StringBuilder();
        for(int i=0; i<tyresPressure.length; i++){
            tmpPress.append("Tire ").append(i).append("pressure: ").append(tyresPressure[i]).append(",");
        }
        return "Bolid id: "+bolidIndex+", Speed: "+speed+", Temperature: "+getTemp()+", pressure in tires: " + tmpPress +" oil pressure: "+oilPressure;
    }
}
