package sve2.service;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import sve2.client.MeasurementClient;
import sve2.dto.MeasurementForCreationDto;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Startup
@ApplicationScoped
public class MeasurementService {
    @Inject
    @RestClient
    MeasurementClient measurementClient;
    @Inject
    Logger logger;

    @ConfigProperty(name = "measurement.delayMillis", defaultValue = "50")
    long delayMillis;

    @PostConstruct
    void sendMeasurement() {
        Duration delay = Duration.ofMillis(delayMillis);
        if (delay.isZero() || delay.isNegative()) {
            throw new IllegalArgumentException("Configured measurement.delayMillis must be greater than zero");
        }

        Multi.createBy().repeating()
                .uni(this::createAndSendMeasurement)
                .withDelay(delay)
                .indefinitely()
                .subscribe().with(
                        unused -> {},
                        failure -> logger.error("Failed to send measurement", failure)
                );
    }

    private Uni<Void> createAndSendMeasurement() {
        List<MeasurementForCreationDto> measurements = new ArrayList<>();
        measurements.add(new MeasurementForCreationDto(
                "Temperature",
                Instant.now(),
                ThreadLocalRandom.current().nextDouble(20.0, 30.0),
                "Celsius",
                "sensor-5555"
        ));
        measurements.add(new MeasurementForCreationDto(
                "Humidity",
                Instant.now(),
                ThreadLocalRandom.current().nextDouble(40.0, 70.0),
                "Percent",
                "sensor-5555"
        ));

        return measurementClient.sendMeasurement(measurements)
                .onItem().invoke(() -> logger.info("Measurements sent successfully"))
                .onFailure().invoke(ex -> logger.warn("Failed to send measurement, will retry", ex))
                .onFailure().retry().withBackOff(Duration.ofSeconds(2), Duration.ofSeconds(30))
                .atMost(Long.MAX_VALUE);
    }
}
