package ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers;

import ca.ulaval.glo4002.cafe.api.shared.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public abstract class GenericExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {
    private final String error;
    private final String message;
    private final int status;

    protected GenericExceptionMapper(String error, String message, int status) {
        this.error = error;
        this.message = message;
        this.status = status;
    }

    @Override
    public Response toResponse(E exception) {
        return Response.status(status)
                .entity(new ErrorResponse(error, message))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
