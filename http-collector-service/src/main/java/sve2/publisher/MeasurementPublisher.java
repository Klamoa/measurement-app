package sve2.publisher;

import io.smallrye.mutiny.Uni;
import sve2.domain.Measurement;

import java.util.List;

public interface MeasurementPublisher {
    Uni<Void> publishMeasurements(List<Measurement> measurements);
}
