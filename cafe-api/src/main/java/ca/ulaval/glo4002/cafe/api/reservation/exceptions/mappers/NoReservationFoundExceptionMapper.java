package ca.ulaval.glo4002.cafe.api.reservation.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.NoReservationFoundException;

public class NoReservationFoundExceptionMapper extends GenericExceptionMapper<NoReservationFoundException> {
    public NoReservationFoundExceptionMapper() {
        super("NO_RESERVATIONS_FOUND", "No reservations were made today for that group.",
              HttpStatus.BAD_REQUEST_400);
    }
}
