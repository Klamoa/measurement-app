package sve2.domain;

import lombok.*;

import java.time.Instant;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Measurement {
    private String name;
    private Instant date;
    private double value;
    private String unit;
    private String sensorId;
}
