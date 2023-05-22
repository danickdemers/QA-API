package ca.ulaval.glo4002.cafe.api.shop.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InsufficientSeatsException;

public class InsufficientSeatsExceptionMapper extends GenericExceptionMapper<InsufficientSeatsException> {
    public InsufficientSeatsExceptionMapper() {
        super("INSUFFICIENT_SEATS", "There are currently no available seats. Please come back later.",
              HttpStatus.BAD_REQUEST_400);
    }
}
