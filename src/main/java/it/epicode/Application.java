package it.epicode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        Pubblicazione libro1 = new Libro(123, "Orgoglio e Pregiudizio", LocalDate.of(1813, 1, 28), 496, "Jane Austen", "Romanzo");
        Pubblicazione libro2 = new Libro(456, "Il Grande Gatsby", LocalDate.of(1925, 4, 10), 180, "F. Scott Fitzgerald", "Narrativa");
        Pubblicazione libro3 = new Libro(789, "1984", LocalDate.of(1949, 6, 8), 328, "George Orwell", "Distopico");

        Pubblicazione rivista1 = new Rivista(101, "National Geographic", LocalDate.of(2023, 10, 1), 100, Periodicita.MENSILE);
        Pubblicazione rivista2 = new Rivista(102, "Scientific American", LocalDate.of(2023, 11, 1), 80, Periodicita.MENSILE);
        Pubblicazione rivista3 = new Rivista(103, "Time Magazine", LocalDate.of(2023, 11, 15), 50, Periodicita.SETTIMANALE);

        Archivio archivio = new Archivio();

        try {
            archivio.aggiungiEl(libro1);
            archivio.aggiungiEl(libro2);
            archivio.aggiungiEl(libro3);
            archivio.aggiungiEl(rivista1);
            archivio.aggiungiEl(rivista2);
            archivio.aggiungiEl(rivista3);
        } catch (ExistingIsbnException e) {
            LOGGER.error("Errore durante l'aggiunta della pubblicazione: {}", e.getMessage());
        }

        archivio.getPubblicazioni().forEach(pub -> pub.stampa());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            LOGGER.info("Scegli una delle seguenti azioni:");
            LOGGER.info("1. Aggiungi pubblicazione");
            LOGGER.info("2. Ricerca per ISBN");
            LOGGER.info("3. Ricerca per autore");
            LOGGER.info("4. Elimina per anno di pubblicazione");
            LOGGER.info("5. Aggiornamento da ISBN");
            LOGGER.info("6. Elimina per ISBN");
            LOGGER.info("7. Statistiche catalogo");
            LOGGER.info("0. Esci");

            try {
                int x = scanner.nextInt();
                scanner.nextLine();

                switch (x) {
                    case 1:
                        LOGGER.info("Inserisci il tipo di pubblicazione scegliendo tra Libro/Rivista");
                        String tipo = scanner.nextLine();
                        LOGGER.info("Inserisci ISBN:");
                        int isbn = scanner.nextInt();
                        scanner.nextLine();
                        LOGGER.info("Inserisci titolo:");
                        String titolo = scanner.nextLine();
                        LOGGER.info("Inserisci il numero di pagine:");
                        int pagine = scanner.nextInt();
                        scanner.nextLine();
                        LOGGER.info("Inserisci anno di pubblicazione (YYYY-MM-DD):");
                        String annoInput = scanner.nextLine();
                        LocalDate anno = null;
                        try {
                            anno = LocalDate.parse(annoInput);
                        } catch (DateTimeParseException e) {
                            LOGGER.error("Formato data non valido. Inserisci una data nel formato YYYY-MM-DD.");
                        }

                        if (tipo.toLowerCase().equals("libro")) {
                            LOGGER.info("Inserisci autore:");
                            String autore = scanner.nextLine();
                            LOGGER.info("Inserisci genere:");
                            String genere = scanner.nextLine();
                            Pubblicazione libro = new Libro(isbn, titolo, anno, pagine, autore, genere);
                            try {
                                archivio.aggiungiEl(libro);
                            } catch (ExistingIsbnException e) {
                                LOGGER.error("Errore nell'aggiungere il libro: {}", e.getMessage());
                            }
                        } else if (tipo.toLowerCase().equals("rivista")) {
                            LOGGER.info("Inserisci periodicità: SETTIMANALE, MENSILE, SEMESTRALE");
                            try {
                                Periodicita periodicita = Periodicita.valueOf(scanner.nextLine().toUpperCase());
                                Pubblicazione rivista = new Rivista(isbn, titolo, anno, pagine, periodicita);
                                try {
                                    archivio.aggiungiEl(rivista);
                                } catch (ExistingIsbnException e) {
                                    LOGGER.error("Errore nell'aggiungere la rivista: {}", e.getMessage());
                                }
                            } catch (IllegalArgumentException e) {
                                LOGGER.error("Periodicità non valida. Scegli tra SETTIMANALE, MENSILE, SEMESTRALE.");
                            }
                        } else {
                            LOGGER.error("Tipo di pubblicazione non riconosciuto.");
                        }
                        break;
                    case 2:
                        LOGGER.info("Inserisci ISBN da cercare:");
                        int isbnRicerca = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            Pubblicazione p = archivio.ricercaPerISBN(isbnRicerca);
                            LOGGER.info("Pubblicazione trovata:");
                            p.stampa();
                        } catch (NotExistingIsbnException e) {
                            LOGGER.error("Errore nella ricerca per ISBN: {}", e.getMessage());
                        }
                        break;
                    case 3:
                        LOGGER.info("Inserisci l'autore da cercare:");
                        String autore = scanner.nextLine();
                        try {
                            Libro libroTrovato = archivio.ricercaPerAutore(autore);
                            LOGGER.info("Libro trovato:");
                            libroTrovato.stampa();
                        } catch (LibroNonTrovatoException e) {
                            LOGGER.error("Errore nella ricerca per autore: {}", e.getMessage());
                        }
                        break;
                    case 4:
                        LOGGER.info("Inserisci anno di pubblicazione (YYYY-MM-DD) per cercare la pubblicazione:");
                        LocalDate annoDiPubblicazione = null;
                        boolean validDate = false;
                        while (!validDate) {
                            String annoInput1 = scanner.nextLine();
                            try {
                                annoDiPubblicazione = LocalDate.parse(annoInput1);
                                validDate = true;
                            } catch (DateTimeParseException e) {
                                LOGGER.error("Formato data non valido. Inserisci una data nel formato YYYY-MM-DD.");
                            }
                        }
                        try {
                            Pubblicazione pubbTrovata = archivio.ricercaAnnoPubblicazione(annoDiPubblicazione);
                            LOGGER.info("Pubblicazione trovata:");
                            pubbTrovata.stampa();
                        } catch (PubblicazioneNonTrovataException e) {
                            LOGGER.error("Errore nella ricerca per anno di pubblicazione: {}", e.getMessage());
                        }
                        break;
                    case 5:
                        LOGGER.info("Inserisci ISBN per modificare la pubblicazione:");
                        int ISBN = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            archivio.aggiornamentoDaIsbn(ISBN);
                        } catch (NotExistingIsbnException e) {
                            LOGGER.error("Errore nell'aggiornamento della pubblicazione: {}", e.getMessage());
                        }
                        break;
                    case 6:
                        LOGGER.info("Inserisci ISBN per eliminare la pubblicazione:");
                        int isbnElimina = scanner.nextInt();
                        scanner.nextLine();
                        try {
                            archivio.eliminaPerISBN(isbnElimina);
                        } catch (NotExistingIsbnException e) {
                            LOGGER.error("Errore nell'eliminazione per ISBN: {}", e.getMessage());
                        }
                        break;
                    case 7:
                        archivio.statisticheCatalogo();
                        break;
                    case 0:
                        LOGGER.info("Uscita dall'app");
                        return;
                    default:
                        LOGGER.error("Scelta non valida.");
                        break;
                }
            } catch (Exception e) {
                LOGGER.error("Errore inatteso: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }
}
