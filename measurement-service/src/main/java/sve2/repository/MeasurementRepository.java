package sve2.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import sve2.domain.Measurement;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class MeasurementRepository implements PanacheRepositoryBase<Measurement, UUID> {

    public Uni<List<Measurement>> findBySensorId(String sensorId) {
        return find("sensorId = ?1 ORDER BY date DESC", sensorId).list();
    }

    public Uni<List<Measurement>> findBySensorIdAndDateRange(String sensorId, Instant from, Instant to) {
        return find("sensorId = ?1 and date >= ?2 and date <= ?3 ORDER BY date DESC", sensorId, from, to).list();
    }

    public Uni<List<Measurement>> findBySensorIds(List<String> sensorIds) {
        return find("sensorId IN (?1) ORDER BY date DESC", sensorIds).list();
    }

    public Uni<List<Measurement>> findBySensorIdsAndDateRange(List<String> sensorIds, Instant from, Instant to) {
        return find("sensorId IN (?1) and date >= ?2 and date <= ?3 ORDER BY date DESC", sensorIds, from, to).list();
    }

    public Uni<List<String>> findAllSensorIds() {
        return find("SELECT DISTINCT m.sensorId FROM Measurement m").project(String.class).list();
    }

    public Uni<Long> deleteMeasurementWithSensorId(String sensorId) {
        return delete("sensorId", sensorId);
    }
}
