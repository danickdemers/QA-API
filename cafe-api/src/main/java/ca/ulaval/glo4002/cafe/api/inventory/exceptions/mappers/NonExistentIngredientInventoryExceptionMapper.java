package ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.NonExistentIngredientInventoryException;

public class NonExistentIngredientInventoryExceptionMapper
        extends GenericExceptionMapper<NonExistentIngredientInventoryException> {
    public NonExistentIngredientInventoryExceptionMapper() {
        super("NON_EXISTENT_INGREDIENT", "The inventory for this ingredient could not be found.",
              HttpStatus.BAD_REQUEST_400);
    }
}
