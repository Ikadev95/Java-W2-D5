package it.epicode;

import java.time.LocalDate;
import java.util.*;

public class Archivio {

    Set<Pubblicazione> pubblicazioni = new HashSet<>();

    public void aggiungiEl(Pubblicazione p) throws ExistingIsbnException {
        if (!pubblicazioni.add(p)) {
            throw new ExistingIsbnException("Esiste già un elemento con lo stesso ISBN!");
        }
        System.out.println("la pubblicazione è stata correttamente aggiunta");
    }

    public Pubblicazione ricercaPerISBN(int isbn) throws NotExistingIsbnException {
        Pubblicazione result = pubblicazioni.stream()
                .filter(pub -> pub.getISBN() == isbn)
                .findFirst()
                .orElse(null);
        if(result == null) throw new NotExistingIsbnException("non esiste una pubblicazione con questo Isbn!!");
        return result;
    }

    public void eliminaPerISBN(int isbn) throws NotExistingIsbnException {
        Pubblicazione elDaEliminare = pubblicazioni.stream()
                .filter(pub -> pub.getISBN() == isbn)
                .findFirst()
                .orElse(null);

        if(elDaEliminare == null) throw new NotExistingIsbnException("non esiste una pubblicazione con questo Isbn!!");

        pubblicazioni.remove(elDaEliminare);
        System.out.println("la pubblicazione è stata correttamente eliminata");
    }

    public Pubblicazione ricercaAnnoPubblicazione (LocalDate data) throws PubblicazioneNonTrovataException {
        Pubblicazione result = pubblicazioni.stream()
                .filter(pub -> pub.getAnnoDiPubblicazione().isEqual(data))
                .findFirst()
                .orElse(null);

        if (result == null) {
            throw new PubblicazioneNonTrovataException("Nessuna pubblicazione trovata per l'anno: " + data);
        }
                return result;

    }

    public Libro ricercaPerAutore(String autore) throws LibroNonTrovatoException {
        Libro result = pubblicazioni.stream()
                .filter(pub -> pub instanceof Libro)
                .map(pub -> (Libro) pub)
                .filter(pub -> pub.getAutore().equals(autore))
                .findFirst()
                .orElse(null);
        if (result == null) {
            throw new LibroNonTrovatoException("Nessun libro trovato con l'autore: " + autore);
        }
        return result;
    }

    public void aggiornamentoDaIsbn(int isbn) throws NotExistingIsbnException {
        Pubblicazione elDaAggiornare = pubblicazioni.stream()
                .filter(pub -> pub.getISBN() == isbn)
                .findFirst()
                .orElse(null);
        if (elDaAggiornare == null) throw new NotExistingIsbnException("Non esiste una pubblicazione con questo ISBN!");

        System.out.println("Pubblicazione trovata: " + elDaAggiornare);

        Scanner scanner = new Scanner(System.in);

        System.out.println("Vuoi modificare il titolo? premi invio per lasciare invariato");
        String titolo = scanner.nextLine();
        if (!titolo.isEmpty()) {
            elDaAggiornare.setTitolo(titolo);
        }

        System.out.println("Vuoi modificare il numero di pagine? premi invio per lasciare invariato");
        String pagineInput = scanner.nextLine();
        if (!pagineInput.isEmpty()) {
            elDaAggiornare.setNumeroPagine(Integer.parseInt(pagineInput));
        }

        System.out.println("Vuoi modificare l'anno di pubblicazione? premi invio per lasciare invariato");
        String annoInput = scanner.nextLine();
        if (!annoInput.isEmpty()) {
            elDaAggiornare.setAnnoDiPubblicazione(LocalDate.parse(annoInput));
        }

        System.out.println("Pubblicazione aggiornata: " + elDaAggiornare);
    }


    public void statisticheCatalogo (){
        long numeroLibri = pubblicazioni.stream()
                .filter(pub -> pub instanceof Libro)
                .count();
        System.out.println("il numero di libri inseriti nel catalogo è: " + numeroLibri);

        long numeroRiviste = pubblicazioni.stream()
                .filter((pub -> pub instanceof  Rivista))
                .count();
        System.out.println("il numero delle riviste presenti nel catalogo è: " + numeroRiviste);

        Pubblicazione elMaxPagine = pubblicazioni.stream()
                .max(Comparator.comparing(Pubblicazione::getNumeroPagine))
                .orElse(null);
        System.out.println("la pubblicazione con il maggior numero di pagine è " + elMaxPagine);

        OptionalDouble mediaPagine = pubblicazioni.stream()
                .mapToDouble(Pubblicazione::getNumeroPagine)
                .average();
        if(mediaPagine.isPresent()) System.out.println("la media delle pagine delle pubblicazioni a catalogo è: " + mediaPagine);
    }

}