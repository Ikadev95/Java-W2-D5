package it.epicode;

import java.time.LocalDate;
import java.util.*;

public class Archivio {

    Set<Pubblicazione> pubblicazioni = new HashSet<>();

    public void aggiungiEl(Pubblicazione p) throws ExistingIsbn {
        if (!pubblicazioni.add(p)) {
            throw new ExistingIsbn("Esiste già un elemento con lo stesso ISBN!");
        }
        System.out.println("la pubblicazione è stata correttamente aggiunta");
    }

    public Pubblicazione ricercaPerISBN(int isbn) throws NotExistingIsbn {
        Pubblicazione result = pubblicazioni.stream()
                .filter(pub -> pub.getISBN() == isbn)
                .findFirst()
                .orElse(null);
        if(result == null) throw new NotExistingIsbn("non esiste una pubblicazione con questo Isbn!!");
        return result;
    }

    public void eliminaPerISBN(int isbn) throws NotExistingIsbn {
        Pubblicazione elDaEliminare = pubblicazioni.stream()
                .filter(pub -> pub.getISBN() == isbn)
                .findFirst()
                .orElse(null);

        if(elDaEliminare == null) throw new NotExistingIsbn("non esiste una pubblicazione con questo Isbn!!");

        pubblicazioni.remove(elDaEliminare);
        System.out.println("la pubblicazione è stata correttamente eliminata");
    }

    public Pubblicazione ricercaAnnoPubblicazione (LocalDate data){
        Pubblicazione result = pubblicazioni.stream()
                .filter(pub -> pub.getAnnoDiPubblicazione().isEqual(data))
                .findFirst()
                .orElse(null);
                return result;
    }

    public Libro ricercaPerAutore(String autore){
        Libro result = pubblicazioni.stream()
                .filter(pub -> pub instanceof Libro)
                .map(pub -> (Libro) pub)
                .filter(pub -> pub.getAutore().equals(autore))
                .findFirst()
                .orElse(null);
        return result;
    }

    public void aggiornamentoDaIsbn (int isbn , Pubblicazione p) throws NotExistingIsbn {
        Pubblicazione elDaAggiornare = pubblicazioni.stream()
                .filter(pub -> pub.getISBN() == isbn)
                .findFirst()
                .orElse(null);
        if(elDaAggiornare == null) throw new NotExistingIsbn("non esiste una pubblicazione con questo Isbn!!");

        pubblicazioni.remove(elDaAggiornare);
        pubblicazioni.add(p);
        System.out.println("la pubblicazione è stata aggiornata correttamente");
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