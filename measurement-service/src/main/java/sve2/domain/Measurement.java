package sve2.domain;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Entity
@ToString
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PUBLIC) // error!!
public class Measurement {
    @Id @GeneratedValue
    private UUID measurementId;
    @Column(columnDefinition = "text")
    private String name;
    private Instant date;
    private double value;
    @Column(columnDefinition = "text")
    private String unit;

    @NotNull
    @Column(columnDefinition = "text")
    private String sensorId;

    public Measurement() {}

    public Measurement(Instant date, double value, String unit, String sensorId) {
        this.date = date;
        this.value = value;
        this.unit = unit;
        this.sensorId = sensorId;
    }
}
