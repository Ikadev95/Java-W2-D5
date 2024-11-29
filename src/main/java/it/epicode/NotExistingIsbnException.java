package it.epicode;

public class NotExistingIsbnException extends Exception {
    NotExistingIsbnException(){}
    public NotExistingIsbnException(String message) {
        super(message);
    }
}
