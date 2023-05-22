package ca.ulaval.glo4002.cafe.api.reservation.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InvalidGroupSizeException;

public class InvalidGroupSizeExceptionMapper extends GenericExceptionMapper<InvalidGroupSizeException> {
    public InvalidGroupSizeExceptionMapper() {
        super("INVALID_GROUP_SIZE", "Groups must reserve at least two seats.",
              HttpStatus.BAD_REQUEST_400);
    }
}
