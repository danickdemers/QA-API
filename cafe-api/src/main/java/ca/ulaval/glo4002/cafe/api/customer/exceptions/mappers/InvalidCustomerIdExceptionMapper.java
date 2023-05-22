package ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.InvalidCustomerIdException;

public class InvalidCustomerIdExceptionMapper extends GenericExceptionMapper<InvalidCustomerIdException> {
    public InvalidCustomerIdExceptionMapper() {
        super("INVALID_CUSTOMER_ID", "The customer does not exist.",
              HttpStatus.NOT_FOUND_404);
    }
}
