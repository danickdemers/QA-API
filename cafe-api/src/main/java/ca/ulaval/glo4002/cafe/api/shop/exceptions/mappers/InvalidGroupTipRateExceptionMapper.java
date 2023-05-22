package ca.ulaval.glo4002.cafe.api.shop.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.InvalidGroupTipRateException;

public class InvalidGroupTipRateExceptionMapper extends GenericExceptionMapper<InvalidGroupTipRateException> {
    public InvalidGroupTipRateExceptionMapper() {
        super("INVALID_GROUP_TIP_RATE", "The group tip rate must be set to a value between 0 to 100.",
              HttpStatus.BAD_REQUEST_400);
    }
}
