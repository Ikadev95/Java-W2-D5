package it.epicode;

import java.time.LocalDate;

abstract public class Pubblicazione {
    private int ISBN;
    private String titolo;
    private LocalDate annoDiPubblicazione;
    private int numeroPagine;

    public Pubblicazione(int ISBN, String titolo, LocalDate annoDiPubblicazione, int numeroPagine) {
        this.ISBN = ISBN;
        this.titolo = titolo;
        this.annoDiPubblicazione = annoDiPubblicazione;
        this.numeroPagine = numeroPagine;
    }

    public int getISBN() {
        return ISBN;
    }

    public LocalDate getAnnoDiPubblicazione() {
        return annoDiPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    public void stampa (){}
}
