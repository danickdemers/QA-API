package ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.customer.exceptions.DuplicateCustomerIdException;

public class DuplicateCustomerIdExceptionMapper extends GenericExceptionMapper<DuplicateCustomerIdException> {
    public DuplicateCustomerIdExceptionMapper() {
        super("DUPLICATE_CUSTOMER_ID", "The customer cannot visit the café multiple times in the same day.",
              HttpStatus.BAD_REQUEST_400);
    }
}
