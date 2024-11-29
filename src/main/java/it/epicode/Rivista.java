package it.epicode;

import java.time.LocalDate;

public class Rivista extends Pubblicazione {
    private int ISBN;
    private String titolo;
    private LocalDate annoDiPubblicazione;
    private int numeroPagine;
    Periodicita periodicita;


    public Rivista(int ISBN, String titolo, LocalDate annoDiPubblicazione, int numeroPagine, Periodicita perodicita) {
        super(ISBN, titolo, annoDiPubblicazione, numeroPagine);
        this.periodicita = perodicita;
    }
    @Override
    public void stampa() {
        super.stampa();
        System.out.println(this.titolo + " " + this.getISBN() + " " + this.annoDiPubblicazione + " " + this.numeroPagine);
    }
}
