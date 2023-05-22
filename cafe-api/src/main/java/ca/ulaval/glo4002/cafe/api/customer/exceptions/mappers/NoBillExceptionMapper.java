package ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.bill.NoBillException;

public class NoBillExceptionMapper extends GenericExceptionMapper<NoBillException> {
    public NoBillExceptionMapper() {
        super("NO_BILL", "The customer needs to do a checkout before receiving his bill.",
              HttpStatus.BAD_REQUEST_400);
    }
}
