package sve2.rest;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import sve2.dto.MeasurementForCreationDto;
import sve2.dto.MeasurementForGetDto;
import sve2.logic.MeasurementLogic;
import sve2.mapper.MeasurementMapper;

import java.time.Instant;
import java.util.List;

@Path("/measurements")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MeasurementResource {

    @Inject
    MeasurementLogic measurementLogic;
    @Inject
    MeasurementMapper measurementMapper;
    @Inject
    Logger logger;

    @GET
    @Path("/sensors")
    public Uni<List<String>> getAllSensorsIds() {
        logger.info("HTTP: Get all sensor ids");
        return measurementLogic.getAllSensorIds();
    }

    @GET
    @Path("/{sensorId}")
    public Uni<List<MeasurementForGetDto>> getMeasurementsBySensorId(
            @PathParam("sensorId") String sensorId,
            @QueryParam("from") String from,
            @QueryParam("to") String to
    ) {
        logger.info("HTTP: Get measurements by sensorId=%s, from=%s, to=%s".formatted(sensorId, from, to));
        if (from == null && to == null) {
            return measurementLogic.getMeasurementsBySensorId(sensorId)
                .onItem().transform(measurements -> measurementMapper.toRestDto(measurements));
        } else if (from != null && to == null) {
            return measurementLogic.getMeasurementsBySensorIdAndDateRange(sensorId, Instant.parse(from), Instant.now())
                .onItem().transform(measurements -> measurementMapper.toRestDto(measurements));
        } else if (from != null) {
            return measurementLogic.getMeasurementsBySensorIdAndDateRange(sensorId, Instant.parse(from), Instant.parse(to))
                .onItem().transform(measurements -> measurementMapper.toRestDto(measurements));
        }
        return Uni.createFrom().item(List.of());
    }

    @POST
    public Uni<Response> createMeasurement(List<MeasurementForCreationDto> measurements) {
        logger.info("HTTP: Create measurement");
        return measurementLogic.insertMeasurements(measurementMapper.toDomainFromDtoList(measurements))
            .onItem().transform(persisted -> Response.status(Response.Status.CREATED).build())
            .onFailure().recoverWithItem(e -> {
                if (e instanceof IllegalArgumentException) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid input data").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
                }
            });
    }

    @DELETE
    @Path("/{sensorId}")
    public Uni<Long> deleteMeasurementBySensorId(@PathParam("sensorId") String sensorId) {
        logger.info("HTTP: Delete measurement by sensorId=%s".formatted(sensorId));
        return measurementLogic.deleteMeasurementBySensorId(sensorId);
    }
}
