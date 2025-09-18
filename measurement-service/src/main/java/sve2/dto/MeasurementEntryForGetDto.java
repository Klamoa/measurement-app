package sve2.dto;

import lombok.*;

import java.time.Instant;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
    public class MeasurementEntryForGetDto {
    private Instant date;
    private double value;
}
