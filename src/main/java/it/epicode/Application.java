package it.epicode;

import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {

        Pubblicazione libro1 = new Libro(123, "Orgoglio e Pregiudizio", LocalDate.of(1813, 1, 28), 496, "Jane Austen", "Romanzo");
        Pubblicazione libro2 = new Libro(456, "Il Grande Gatsby", LocalDate.of(1925, 4, 10), 180, "F. Scott Fitzgerald", "Narrativa");
        Pubblicazione libro3 = new Libro(789, "1984", LocalDate.of(1949, 6, 8), 328, "George Orwell", "Distopico");

        Pubblicazione rivista1 = new Rivista(101, "National Geographic", LocalDate.of(2023, 10, 1), 100, Periodicita.MENSILE);
        Pubblicazione rivista2 = new Rivista(102, "Scientific American", LocalDate.of(2023, 11, 1), 80, Periodicita.MENSILE);
        Pubblicazione rivista3 = new Rivista(103, "Time Magazine", LocalDate.of(2023, 11, 15), 50, Periodicita.SETTIMANALE);
    }
}
