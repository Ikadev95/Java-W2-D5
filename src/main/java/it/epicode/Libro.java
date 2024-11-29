package it.epicode;

import java.time.LocalDate;

public class Libro extends Pubblicazione {
    private int ISBN;
    private String titolo;
    private LocalDate annoDiPubblicazione;
    private int numeroPagine;
    private String autore;
    private String genere;


    public Libro(int ISBN, String titolo, LocalDate annoDiPubblicazione, int numeroPagine, String autore, String genere) {
        super(ISBN, titolo, annoDiPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;

    }

    @Override
    public void stampa() {
        super.stampa();
        System.out.println(this.titolo + " " + this.getISBN() + " " + this.annoDiPubblicazione + " " + this.numeroPagine);
    }
}
