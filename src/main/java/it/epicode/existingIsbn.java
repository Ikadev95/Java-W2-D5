package it.epicode;

public class existingIsbn extends Exception {
    public existingIsbn(){}
    public existingIsbn(String message) {
        super(message);
    }
}
