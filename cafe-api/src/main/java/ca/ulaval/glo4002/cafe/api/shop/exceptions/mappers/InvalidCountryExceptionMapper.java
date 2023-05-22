package ca.ulaval.glo4002.cafe.api.shop.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.shop.taxer.InvalidCountryException;

public class InvalidCountryExceptionMapper extends GenericExceptionMapper<InvalidCountryException> {
    public InvalidCountryExceptionMapper() {
        super("INVALID_COUNTRY", "The specified country is invalid.",
              HttpStatus.BAD_REQUEST_400);
    }
}
