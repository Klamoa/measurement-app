package sve2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sve2.domain.Measurement;
import sve2.dto.MeasurementForCreationDto;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface MeasurementMapper {
    Measurement toDomain(MeasurementForCreationDto measurement);
    List<Measurement> toDomain(List<MeasurementForCreationDto> measurement);
}
