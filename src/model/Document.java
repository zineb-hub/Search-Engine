package model;

import java.util.ArrayList;

/**
 * La classe `Document` représente un document contenant un titre
 * et une liste de mots "Mot".
 * Elle étend la classe `ArrayList<Mot>` pour gérer la collection des mots du document.
 */
public class Document extends ArrayList<Mot> {
    private String title; // Titre du document

    /**
     * Constructeur de la classe `Document`.
     * Initialise le titre du document et crée une liste vide de mots.
     *
     * @param title Le titre du document.
     */
    public Document(String title) {
        super();
        this.title = title;
    }

    /**
     * Retourne le titre du document.
     *
     * @return Le titre du document.
     */
    public String getTitre() {
        return title;
    }

    /**
     * Ajoute un mot au document après l'avoir transformé en un objet `Mot`.
     *
     * @param mot Le mot à ajouter au document.
     */
    public void putMot(String mot) {
    	if (mot != null && !mot.trim().isEmpty() && mot.matches("[a-zA-Z]+")) {
                 Mot Mot = new Mot(mot); 
                 this.add(Mot); 
         }        
    }

    /**
     * Génère une représentation sous forme de chaîne de caractères du document.
     * Affiche le titre et tous les mots du document.
     *
     * @return Une chaîne représentant le document.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titre: ").append(title).append("\n"); // Ajoute le titre
        sb.append("    Mots: ");
        for (Mot mot : this) { // Parcourt tous les mots d e chaque document
            sb.append(mot.getMot()).append(" "); // Ajoute chaque mot à la chaîne
        }
        return sb.toString();
    }
}