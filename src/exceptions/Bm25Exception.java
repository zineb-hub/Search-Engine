package exceptions;

/**
 * La classe `Bm25Exception` représente une exception spécifique
 * liée aux erreurs survenant lors de l'utilisation du modèle BM25.
 * Elle hérite de la classe `MoteurRechercheException`.
 */
public class Bm25Exception extends MoteurRechercheException {

    /**
     * Constructeur de la classe `Bm25Exception`.
     * Permet de spécifier un message d'erreur lors de la création de l'exception.
     *
     * @param message Le message décrivant l'erreur.
     */
    public Bm25Exception(String message) {
        super(message); // Appelle le constructeur de la classe parente
    }
}