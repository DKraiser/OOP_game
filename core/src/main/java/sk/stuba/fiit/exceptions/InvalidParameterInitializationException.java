package sk.stuba.fiit.exceptions;

/**
 * Custom exception class that represents an error occurring due to invalid
 * initialization parameters. This exception extends {@link RuntimeException} and
 * is thrown when the application detects that the parameters used during initialization
 * are incorrect or invalid.
 */
public class InvalidParameterInitializationException extends RuntimeException {
    private String message;

    /**
     * Retrieves the error message associated with the exception.
     *
     * @return The error message describing the issue with parameter initialization.
     */
    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Constructs a new InvalidParameterInitializationException with a default error message.
     * The default message indicates that there is an issue with the initialization parameters.
     */
    public InvalidParameterInitializationException() {
        this.message = "Check your init parameters";
    }
}
