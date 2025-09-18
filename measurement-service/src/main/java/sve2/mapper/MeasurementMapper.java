package sve2.mapper;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sve2.domain.Measurement;
import sve2.dto.MeasurementEntryForGetDto;
import sve2.dto.MeasurementForCreationDto;
import sve2.dto.MeasurementForGetDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Mapper(componentModel = "cdi")
public interface MeasurementMapper {
    record NameUnitKey(String name, String unit) {}

    default List<MeasurementForGetDto> toRestDto(List<Measurement> measurements) {
        Map<NameUnitKey, List<Measurement>> grouped = measurements.stream()
                .collect(Collectors.groupingBy(m -> new NameUnitKey(m.getName(), m.getUnit())));

        return grouped.entrySet().stream()
                .map(entry -> {
                    NameUnitKey key = entry.getKey();
                    List<MeasurementEntryForGetDto> entries = entry.getValue().stream()
                            .map(m -> new MeasurementEntryForGetDto(m.getDate(), m.getValue()))
                            .toList();
                    return new MeasurementForGetDto(key.name(), key.unit(), entries);
                })
                .toList();
    }

    @Mapping(target = "measurementId", ignore = true)
    Measurement toDomain(MeasurementForCreationDto measurement);
    List<Measurement> toDomainFromDtoList(List<MeasurementForCreationDto> measurement);
    default List<Measurement> toDomain(JsonArray jsonMeasurements) {
        return jsonMeasurements.stream().map(obj -> {
            return ((JsonObject)obj).mapTo(Measurement.class);
        }).toList();
    }
    default List<Measurement> toDomainFromJsonArrayList(List<JsonArray> jsonMeasurements) {
        return jsonMeasurements.stream().flatMap(array -> {
            return array.stream().map(obj -> {
                return ((JsonObject)obj).mapTo(Measurement.class);
            });
        }).toList();
    }
}
