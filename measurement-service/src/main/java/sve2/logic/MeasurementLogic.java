package sve2.logic;

import io.smallrye.mutiny.Uni;
import sve2.domain.Measurement;

import java.time.Instant;
import java.util.List;

public interface MeasurementLogic {
    Uni<List<Measurement>> getMeasurementsBySensorId(String sensorId);
    Uni<List<Measurement>> getMeasurementsBySensorIdAndDateRange(String sensorId, Instant from, Instant to);
    Uni<List<Measurement>> getMeasurementsBySensorIds(List<String> sensorIds);
    Uni<List<Measurement>> getMeasurementsBySensorIdsAndDateRange(List<String> sensorIds, Instant from, Instant to);
    Uni<List<String>> getAllSensorIds();
    Uni<Void> insertMeasurements(List<Measurement> measurements);
    Uni<Long> deleteMeasurementBySensorId(String sensorId);
}
