package sve2.logic;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import sve2.domain.Measurement;
import sve2.repository.MeasurementRepository;

import java.time.Instant;
import java.util.List;

@RequestScoped
public class MeasurementLogicImpl implements MeasurementLogic {
    @Inject
    MeasurementRepository measurementRepository;

    @Override
    @WithSession
    public Uni<List<Measurement>> getMeasurementsBySensorId(String sensorId) {
        return measurementRepository.findBySensorId(sensorId);
    }

    @Override
    @WithSession
    public Uni<List<Measurement>> getMeasurementsBySensorIdAndDateRange(String sensorId, Instant from, Instant to) {
        return measurementRepository.findBySensorIdAndDateRange(sensorId, from, to);
    }

    @Override
    @WithSession
    public Uni<List<Measurement>> getMeasurementsBySensorIds(List<String> sensorIds) {
        return measurementRepository.findBySensorIds(sensorIds);
    }

    @Override
    @WithSession
    public Uni<List<Measurement>> getMeasurementsBySensorIdsAndDateRange(List<String> sensorIds, Instant from, Instant to) {
        return measurementRepository.findBySensorIdsAndDateRange(sensorIds, from, to);
    }

    @Override
    @WithSession
    public Uni<List<String>> getAllSensorIds() {
        return measurementRepository.findAllSensorIds();
    }

    @Override
    @WithTransaction
    public Uni<Void> insertMeasurements(List<Measurement> measurements) {
        return Uni.createFrom().item(() -> {
            measurements.forEach(m -> {
                if (m.getDate() == null) {
                    m.setDate(Instant.now());
                }
            });
            return measurements;
        })
        .onItem().transformToUni(updated -> measurementRepository.persist(updated));
    }

    @Override
    @WithTransaction
    public Uni<Long> deleteMeasurementBySensorId(String sensorId) {
        return measurementRepository.deleteMeasurementWithSensorId(sensorId);
    }
}
