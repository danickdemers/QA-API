package ca.ulaval.glo4002.cafe.api.inventory.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers.GenericExceptionMapper;
import ca.ulaval.glo4002.cafe.domain.inventory.exceptions.IncompatibleUnitsOfMeasureException;

public class IncompatibleUnitsOfMeasureExceptionMapper
        extends GenericExceptionMapper<IncompatibleUnitsOfMeasureException> {
    public IncompatibleUnitsOfMeasureExceptionMapper() {
        super("INCOMPATIBLE_UNITS_OF_MEASURE", "The two quantities had different units of measures.",
              HttpStatus.BAD_REQUEST_400);
    }
}
