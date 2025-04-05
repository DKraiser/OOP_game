package sk.stuba.fiit.exceptions;

public class InvalidParameterInitializationException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public InvalidParameterInitializationException() {
        this.message = "Check your init parameters";
    }
}
