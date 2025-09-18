package sve2.mapper;

import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import sve2.dto.ErrorResponse;

@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Throwable> {
    @Inject
    Logger logger;

    @Override
    public Response toResponse(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);

        switch (throwable) {
            case WebApplicationException webApplicationException -> {
                return createErrorResponse(
                        webApplicationException.getResponse().getStatusInfo().toEnum(),
                        webApplicationException.getResponse().getStatusInfo().getReasonPhrase(),
                        throwable.getMessage());
            }

            default -> {
                return createErrorResponse(Response.Status.INTERNAL_SERVER_ERROR, "Internal Server Error", throwable.getMessage());
            }
        }
    }

    private Response createErrorResponse(Response.Status status, String errorType, String message) {
        ErrorResponse errorResponse = new ErrorResponse(errorType, message != null ? message : "An unexpected error occurred.");
        return Response.status(status)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
