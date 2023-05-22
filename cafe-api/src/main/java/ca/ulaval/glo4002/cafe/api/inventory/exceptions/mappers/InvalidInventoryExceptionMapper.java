package ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.inventory.exceptions.InvalidInventoryException;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;

public class InvalidInventoryExceptionMapper extends GenericExceptionMapper<InvalidInventoryException> {
    public InvalidInventoryExceptionMapper() {
        super("INVALID_INVENTORY", "The given inventory is not valid.",
              HttpStatus.BAD_REQUEST_400);
    }
}
