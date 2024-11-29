package it.epicode;

import java.time.LocalDate;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        Pubblicazione libro1 = new Libro(123, "Orgoglio e Pregiudizio", LocalDate.of(1813, 1, 28), 496, "Jane Austen", "Romanzo");
        Pubblicazione libro2 = new Libro(456, "Il Grande Gatsby", LocalDate.of(1925, 4, 10), 180, "F. Scott Fitzgerald", "Narrativa");
        Pubblicazione libro3 = new Libro(789, "1984", LocalDate.of(1949, 6, 8), 328, "George Orwell", "Distopico");

        Pubblicazione rivista1 = new Rivista(101, "National Geographic", LocalDate.of(2023, 10, 1), 100, Periodicita.MENSILE);
        Pubblicazione rivista2 = new Rivista(102, "Scientific American", LocalDate.of(2023, 11, 1), 80, Periodicita.MENSILE);
        Pubblicazione rivista3 = new Rivista(103, "Time Magazine", LocalDate.of(2023, 11, 15), 50, Periodicita.SETTIMANALE);

        Archivio archivio = new Archivio();

        try{
            archivio.aggiungiEl(libro1);
            archivio.aggiungiEl(libro2);
            archivio.aggiungiEl(libro3);
            archivio.aggiungiEl(rivista1);
            archivio.aggiungiEl(rivista2);
            archivio.aggiungiEl(rivista3);
        } catch (ExistingIsbnException e) {
            System.out.println(e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        while(true){
            System.out.println("scegli una delle seguenti azioni: ");
            System.out.println("1. Aggiungi pubblicazione");
            System.out.println("2. Ricerca per ISBN");
            System.out.println("3. Ricerca per autore");
            System.out.println("4. Elimina per anno di pubblicazione");
            System.out.println("5. Aggiornamento da ISBN");
            System.out.println("6. Elimina per ISBN");
            System.out.println("7. Statistiche catalogo");
            System.out.println("0. Esci");


            try{
                int x = scanner.nextInt();

                switch (x){
                    case 1:
                        System.out.println("inserisci il tipo di pubblicazione scegliendo tra Libro/Rivista");
                        String tipo = scanner.nextLine();
                        System.out.println("inserisci ISBN:");
                        int isbn = scanner.nextInt();
                        System.out.println("Inserisci titolo:");
                        String titolo = scanner.nextLine();
                        System.out.println("inserisci il numero di pagine:");
                        int pagine = scanner.nextInt();
                        System.out.println("Inserisci anno di pubblicazione (YYYY-MM-DD):");
                        LocalDate anno = LocalDate.parse(scanner.nextLine());

                        if(tipo.toLowerCase().equals("libro")){
                            System.out.println("Inserisci autore:");
                            String autore = scanner.nextLine();
                            System.out.println("Inserisci genere:");
                            String genere = scanner.nextLine();
                            Pubblicazione libro = new Libro(isbn,titolo,anno,pagine,autore,genere);
                            try {
                                archivio.aggiungiEl(libro);
                            } catch (ExistingIsbnException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        else if(tipo.toLowerCase().equals("rivista")){
                            System.out.println("Inserisci periodicità: SETTIMANALE, MENSILE, SEMESTRALE");
                            try {
                                Periodicita periodicita = Periodicita.valueOf(scanner.nextLine().toUpperCase());
                                Pubblicazione rivista = new Rivista(isbn, titolo, anno, pagine, periodicita);
                                try {
                                    archivio.aggiungiEl(rivista);
                                } catch (ExistingIsbnException e) {
                                    System.out.println(e.getMessage());
                                }
                            } catch (IllegalArgumentException e) {
                                System.out.println("Periodicità non valida. Scegli tra SETTIMANALE, MENSILE, SEMESTRALE.");
                            }
                        } else {
                            System.out.println("Tipo di pubblicazione non riconosciuto.");
                        }
                        break;
                    case 2:
                        System.out.println("Inserisci ISBN da cercare:");
                        int isbnRicerca = scanner.nextInt();
                        try {
                            Pubblicazione p = archivio.ricercaPerISBN(isbnRicerca);
                            System.out.println("Pubblicazione trovata: " + p);
                        } catch (NotExistingIsbnException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 3:
                        System.out.println("Inserisci l'autore da cercare:");
                        String autore = scanner.nextLine();
                        try {
                            Libro libroTrovato = archivio.ricercaPerAutore(autore);
                            System.out.println("Libro trovato: " + libroTrovato);
                        } catch (LibroNonTrovatoException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.println("Inserisci anno di pubblicazione (YYYY-MM-DD) per cercare la pubblicazione:");
                        LocalDate annoDiPubblicazione = LocalDate.parse(scanner.nextLine());
                        try{
                            Pubblicazione pubbTrovata = archivio.ricercaAnnoPubblicazione(annoDiPubblicazione);
                            System.out.println("Pubblicazione trovata: " + pubbTrovata);
                        }
                        catch (PubblicazioneNonTrovataException e){
                            System.out.println(e.getMessage());
                        }
                    case 5:
                        System.out.println("Inserisci ISBN per modificare la bubblicazione:");
                        int ISBN = scanner.nextInt();
                        try{
                            archivio.aggiornamentoDaIsbn(ISBN);
                        }
                        catch ()
                    case 6:
                        System.out.println("Inserisci ISBN per eliminare la bubblicazione:");
                        int isbnElimina = scanner.nextInt();
                        try {
                            archivio.eliminaPerISBN(isbnElimina);
                        } catch (NotExistingIsbnException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 7:
                        archivio.statisticheCatalogo();
                        break;
                    case 0:
                        System.out.println("Uscita dall'app");
                        break;
                    default:
                        System.out.println("Scelta non valida");
                        break;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
