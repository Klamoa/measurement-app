package sve2.logic;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sve2.domain.Measurement;
import sve2.publisher.MeasurementPublisher;

import java.time.Instant;
import java.util.List;

@RequestScoped
public class MeasurementLogicImpl implements MeasurementLogic {
    @Inject
    MeasurementPublisher publisher;

    @Override
    public Uni<Void> forwardMeasurements(List<Measurement> measurements) {
        return Uni.createFrom().item(() -> {
            measurements.forEach(m -> {
                if (m.getDate() == null) {
                    m.setDate(Instant.now());
                }
            });
            return measurements;
        })
        .call(publisher::publishMeasurements).replaceWithVoid();
    }
}
