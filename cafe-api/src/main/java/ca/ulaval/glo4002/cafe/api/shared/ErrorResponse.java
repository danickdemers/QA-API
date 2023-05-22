package ca.ulaval.glo4002.cafe.api.shared;

public class ErrorResponse {
    public final String error;
    public final String description;

    public ErrorResponse(String errorCode, String description) {
        this.error = errorCode;
        this.description = description;
    }
}
