package ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.inventory.exceptions.InvalidInventoryRequestException;
import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;

public class InvalidInventoryRequestExceptionMapper extends GenericExceptionMapper<InvalidInventoryRequestException> {
    public InvalidInventoryRequestExceptionMapper() {
        super("INVALID_INVENTORY_REQUEST", "The inventory request is not valid.",
              HttpStatus.BAD_REQUEST_400);
    }
}
