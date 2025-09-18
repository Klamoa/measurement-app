package sve2.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementForGetDto {
    private String name;
    private String unit;
    private List<MeasurementEntryForGetDto> entries;
}
