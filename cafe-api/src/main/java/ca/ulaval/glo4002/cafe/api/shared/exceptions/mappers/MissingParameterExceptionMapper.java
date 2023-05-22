package ca.ulaval.glo4002.cafe.api.shared.exceptions.mappers;

import org.eclipse.jetty.http.HttpStatus;

import ca.ulaval.glo4002.cafe.api.shared.exceptions.MissingParameterException;

public class MissingParameterExceptionMapper extends GenericExceptionMapper<MissingParameterException> {
    public MissingParameterExceptionMapper() {
        super("MISSING_PARAMETER", "A parameter is missing.",
              HttpStatus.BAD_REQUEST_400);
    }
}
