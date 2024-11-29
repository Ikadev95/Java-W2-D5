package it.epicode;

public class ExistingIsbn extends Exception {
    public ExistingIsbn(){}
    public ExistingIsbn(String message) {
        super(message);
    }
}
