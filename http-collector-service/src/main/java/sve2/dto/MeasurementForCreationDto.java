package sve2.dto;

import lombok.*;

import java.time.Instant;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementForCreationDto {
    private String name;
    private Instant date;
    private double value;
    private String unit;
    private String sensorId;
}
