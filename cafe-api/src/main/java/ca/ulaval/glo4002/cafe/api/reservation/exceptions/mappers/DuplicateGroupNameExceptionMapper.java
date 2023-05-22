package ca.ulaval.glo4002.cafe.api.reservation.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.shop.exceptions.DuplicateGroupNameException;

public class DuplicateGroupNameExceptionMapper extends GenericExceptionMapper<DuplicateGroupNameException> {
    public DuplicateGroupNameExceptionMapper() {
        super("DUPLICATE_GROUP_NAME", "The specified group already made a reservation today.",
              HttpStatus.BAD_REQUEST_400);
    }
}
