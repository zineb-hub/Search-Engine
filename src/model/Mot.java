package model;

import org.tartarus.snowball.ext.porterStemmer;

/**
 * La classe `Mot` représente un mot avec une normalisation (minuscule et racinisation).
 * Elle fournit des méthodes pour comparer et manipuler les mots en tant qu'objets.
 */
public class Mot {
    private String mot; // Représentation normalisée du mot

    /**
     * Constructeur de la classe `Mot`.
     * Le mot est transformé en minuscule et racinisé (stemming).
     *
     * @param mot Le mot à normaliser et raciniser.
     */
    public Mot(String mot) {
        this.mot = stemWord(mot.toLowerCase());
    }

    /**
     * Retourne la représentation normalisée du mot.
     *
     * @return Le mot normalisé.
     */
    public String getMot() {
        return mot;
    }

    /**
     * Applique un algorithme de racinisation (Porter Stemmer) au mot.
     *
     * @param word Le mot à raciniser.
     * @return La version racinisée du mot.
     */
    private String stemWord(String word) {
        porterStemmer stemmer = new porterStemmer();
        stemmer.setCurrent(word); // Définit le mot à traiter
        stemmer.stem(); // Applique l'algorithme de racinisation
        return stemmer.getCurrent(); // Retourne le mot racinisé
    }

    /**
     * Vérifie si deux objets `Mot` sont égaux.
     * Deux objets `Mot` sont égaux si leurs représentations normalisées sont identiques.
     *
     * @param obj L'objet à comparer.
     * @return `true` si les objets sont égaux, `false` sinon.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Vérifie si c'est le même objet
        if (obj == null || getClass() != obj.getClass()) return false; // Vérifie la classe
        Mot mot1 = (Mot) obj;
        return mot.equals(mot1.mot); // Compare les chaînes de caractères
    }

    /**
     * Retourne le code de hachage (hash code) du mot.
     * Utile pour utiliser des objets `Mot` dans des collections comme `HashMap` ou `HashSet`.
     *
     * @return Le code de hachage du mot.
     */
    @Override
    public int hashCode() {
        return (mot != null) ? mot.hashCode() : 0;
    }
}