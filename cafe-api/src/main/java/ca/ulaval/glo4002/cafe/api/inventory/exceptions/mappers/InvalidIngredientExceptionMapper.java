package ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.InvalidIngredientException;

public class InvalidIngredientExceptionMapper extends GenericExceptionMapper<InvalidIngredientException> {
    public InvalidIngredientExceptionMapper() {
        super("INVALID_INGREDIENT", "The given ingredient is not valid.",
              HttpStatus.BAD_REQUEST_400);
    }
}
