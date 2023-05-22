package ca.ulaval.glo4002.cafe.api.customer.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.InsufficientIngredientsException;

public class InsufficientIngredientsExceptionMapper extends GenericExceptionMapper<InsufficientIngredientsException> {

    public InsufficientIngredientsExceptionMapper() {
        super("INSUFFICIENT_INGREDIENTS",  "We lack the necessary number of ingredients to fulfill your order.",
              HttpStatus.BAD_REQUEST_400);
    }
}
