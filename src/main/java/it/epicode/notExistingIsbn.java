package it.epicode;

public class notExistingIsbn extends Exception {
    notExistingIsbn(){}
    public notExistingIsbn(String message) {
        super(message);
    }
}
