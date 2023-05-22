package ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.AlreadyPresentIngredientInventoryException;

public class AlreadyPresentIngredientInventoryExceptionMapper
        extends GenericExceptionMapper<AlreadyPresentIngredientInventoryException> {
    public AlreadyPresentIngredientInventoryExceptionMapper() {
        super("ALREADY_PRESENT_INGREDIENT_INVENTORY", "The inventory for this ingredient is already present.",
              HttpStatus.BAD_REQUEST_400);
    }
}
