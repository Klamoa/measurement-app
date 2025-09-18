package sve2.logic;

import io.smallrye.mutiny.Uni;
import sve2.domain.Measurement;

import java.util.List;

public interface MeasurementLogic {
    Uni<Void> forwardMeasurements(List<Measurement> measurements);
}
