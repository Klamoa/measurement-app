package sve2.rest;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import sve2.dto.MeasurementForCreationDto;
import sve2.logic.MeasurementLogic;
import sve2.mapper.MeasurementMapper;

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

    @POST
    public Uni<Response> createMeasurement(List<MeasurementForCreationDto> measurements) {
        logger.info("HTTP: Create measurement");
        return measurementLogic.forwardMeasurements(measurementMapper.toDomain(measurements))
            .onItem().transform(persisted -> Response.status(Response.Status.CREATED).build())
            .onFailure().recoverWithItem(e -> {
                if (e instanceof IllegalArgumentException) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Invalid input data").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal server error").build();
                }
            });
    }
}
