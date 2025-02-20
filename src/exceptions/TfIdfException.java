package exceptions;

/**
 * La classe `TfIdfException` représente une exception spécifique
 * liée aux erreurs survenant lors de l'utilisation du modèle TF-IDF.
 * Elle hérite de la classe `MoteurRechercheException`.
 */
public class TfIdfException extends MoteurRechercheException {

    /**
     * Constructeur de la classe `TfIdfException`.
     * Permet de spécifier un message d'erreur lors de la création de l'exception.
     *
     * @param message Le message décrivant l'erreur.
     */
    public TfIdfException(String message) {
        super(message); // Appelle le constructeur de la classe parente
    }
}