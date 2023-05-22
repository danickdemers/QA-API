package ca.ulaval.glo4002.cafe.api.shop.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.api.shop.exceptions.InvalidGroupReservationMethodException;

public class InvalidGroupReservationMethodExceptionMapper
        extends GenericExceptionMapper<InvalidGroupReservationMethodException> {
    public InvalidGroupReservationMethodExceptionMapper() {
        super("INVALID_GROUP_RESERVATION_METHOD", "The group reservation method is not supported.",
              HttpStatus.BAD_REQUEST_400);
    }
}
