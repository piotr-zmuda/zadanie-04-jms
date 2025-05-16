package bolidApplication;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BolidMessage {

    private static long idIndex = 0;

    public static long nextId() {
        return idIndex++;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    private long id;

    private int bolidId;

    private long speed;

    private long temp;

    private int [] tyresPressure;

    private long oilPressure;

    private int racePlaceNb;

    private long bestLap;

    private long lapNb;

    private int bolidIndex;

    private String message;

    private Status bolidStatus;

}
