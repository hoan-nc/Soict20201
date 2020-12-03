package application.model;

import org.springframework.http.HttpStatus;

public enum MetaDataStatus {
    NO_DATA("No data", HttpStatus.NO_CONTENT),
    FAILED("Failed", HttpStatus.INTERNAL_SERVER_ERROR),
    SUCCESS("Success", HttpStatus.OK),
    CREATED("Created", HttpStatus.CREATED),
    BAD_REQUEST("Bad Request", HttpStatus.BAD_REQUEST),
    METHOD_NOT_ALLOWED("Method not allowed", HttpStatus.METHOD_NOT_ALLOWED);

    private String message;
    private HttpStatus statusCode;

    MetaDataStatus(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
