package sve2.subscriber;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.subscription.BackPressureStrategy;
import io.smallrye.mutiny.subscription.MultiEmitter;
import io.vertx.core.json.JsonArray;
import io.vertx.mutiny.core.Vertx;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;
import sve2.logic.MeasurementLogic;
import sve2.mapper.MeasurementMapper;

import java.time.Duration;
import java.util.List;

@ApplicationScoped
public class MeasurementSubscriberRabbitMq {
    @Inject
    Vertx vertx;
    @Inject
    MeasurementLogic measurementLogic;
    @Inject
    MeasurementMapper measurementMapper;
    @Inject
    Logger logger;

    @ConfigProperty(name = "batching.batch-size", defaultValue = "100")
    int batchSize;

    @ConfigProperty(name = "batching.maximum-delay", defaultValue = "5")
    int maximumDelay;

    private MultiEmitter<? super Message<JsonArray>> emitterRef;

    @PostConstruct
    void init() {
        logger.info("Initializing batching logic");

        Multi.createFrom()
            .emitter((MultiEmitter<? super Message<JsonArray>> emitter) -> emitterRef = emitter, BackPressureStrategy.BUFFER)
            .group().intoLists().of(batchSize, Duration.ofSeconds(maximumDelay))
            .emitOn(runnable -> vertx.runOnContext(runnable)) // let downstream run on event loop, needed for @ActivationRequestContext
            .onItem().transformToUniAndConcatenate(this::processMeasurementBatch)
            .subscribe().with(unused -> {}, failure -> logger.error("Fatal error in batch processing", failure));
    }

    @Incoming("measurements")
    public Uni<Void> receiveMeasurements(Message<JsonArray> message) {
        logger.info("RabbitMQ: Create measurement");
        return Uni.createFrom().voidItem().onItem().invoke(() -> emitterRef.emit(message));
    }

    @ActivateRequestContext
    public Uni<Void> processMeasurementBatch(List<Message<JsonArray>> batch) {
        return Uni.createFrom().item(() -> batch)
                .onItem().transformToUni(batchList -> {
                    logger.info("Saving batch of " + batchList.size() + " elements");
                    List<JsonArray> payloads = batchList.stream().map(Message::getPayload).toList();

                    return measurementLogic.insertMeasurements(measurementMapper.toDomainFromJsonArrayList(payloads))
                            .invoke(() -> {
                                batchList.forEach(Message::ack);
                                logger.info("RabbitMQ: Acknowledged batch of " + batchList.size() + " elements");
                            });
                });
    }
}
