package bolidApplication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PitStopResponseMessage {
    private String message;

    private boolean answer;
}
