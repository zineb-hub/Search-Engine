package exceptions;

/**
 * La classe `CorpusException` représente une exception spécifique
 * liée aux erreurs survenant lors de la manipulation d'un corpus.
 * Elle hérite de la classe `MoteurRechercheException`.
 */
public class CorpusException extends MoteurRechercheException {

    /**
     * Constructeur de la classe `CorpusException`.
     * Permet de spécifier un message d'erreur lors de la création de l'exception.
     *
     * @param message Le message décrivant l'erreur.
     */
    public CorpusException(String message) {
        super(message); // Appelle le constructeur de la classe parente
    }
}