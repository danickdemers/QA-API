package ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.InvalidMenuOrderException;

public class InvalidMenuOrderExceptionMapper extends GenericExceptionMapper<InvalidMenuOrderException> {
    public InvalidMenuOrderExceptionMapper() {
        super("INVALID_MENU_ORDER", "An item ordered is not on the menu.",
              HttpStatus.BAD_REQUEST_400);
    }
}
