package exceptions;

/**
 * La classe `MoteurRechercheException` est une exception personnalisée
 * utilisée comme classe de base pour toutes les exceptions dans le moteur de recherche.
 * Elle fournit une représentation détaillée de l'exception, incluant sa hiérarchie.
 */
public class MoteurRechercheException extends Exception {

    /**
     * Constructeur de la classe `MoteurRechercheException`.
     * Permet de spécifier un message d'erreur lors de la création de l'exception.
     *
     * @param message Le message décrivant l'erreur.
     */
    public MoteurRechercheException(String message) {
        super(message);
    }

    /**
     * Retourne une chaîne de caractères décrivant l'exception,
     * incluant sa hiérarchie et son message.
     *
     * @return Une chaîne détaillée de l'exception.
     */
    @Override
    public String toString() {
    	return getClass().getName() + ": " + getMessage();    
    }
}