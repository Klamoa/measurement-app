package sve2.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import sve2.dto.MeasurementForCreationDto;

import java.util.List;

@Path("/api/measurements")
@RegisterRestClient(configKey = "measurement-api")
public interface MeasurementClient {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ClientHeaderParam(name = "Connection", value = "close")
    Uni<Void> sendMeasurement(List<MeasurementForCreationDto> measurements);
}
