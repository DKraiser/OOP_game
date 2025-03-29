package sk.stuba.fiit.exceptions;

public class EmptyCollectionEnumeratingException extends RuntimeException {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public EmptyCollectionEnumeratingException(String message) {
        this.message = message;
    }
}
