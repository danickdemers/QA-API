package ca.ulaval.glo4002.cafe.api.reservation.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.NoGroupSeatsException;

public class NoGroupSeatsExceptionMapper extends GenericExceptionMapper<NoGroupSeatsException> {
    public NoGroupSeatsExceptionMapper() {
        super("NO_GROUP_SEATS", "There are no more seats reserved for that group.",
              HttpStatus.BAD_REQUEST_400);
    }
}
