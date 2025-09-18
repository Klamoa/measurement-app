package sve2.publisher;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import sve2.domain.Measurement;

import java.util.List;

@ApplicationScoped
public class MeasurementPublisherRabbitMq implements MeasurementPublisher {
    @Inject
    Logger logger;

    @Channel("measurements")
    Emitter<List<Measurement>> measurementsEmitter;

    @Override
    public Uni<Void> publishMeasurements(List<Measurement> measurements) {
        logger.info("RabbitMQ: Publishing measurements");
        return Uni.createFrom().completionStage(measurementsEmitter.send(measurements));
    }
}
