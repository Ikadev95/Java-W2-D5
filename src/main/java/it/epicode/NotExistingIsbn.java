package it.epicode;

public class NotExistingIsbn extends Exception {
    NotExistingIsbn(){}
    public NotExistingIsbn(String message) {
        super(message);
    }
}
