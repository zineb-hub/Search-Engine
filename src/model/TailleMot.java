package model;

import engine.Corpus;

/**
 * La classe `TailleMot` implémente l'interface `Taille`.
 * Elle est utilisée pour calculer le nombre total de mots dans un corpus.
 */
public class TailleMot implements Taille {

    /**
     * Calcule le nombre total de mots dans le corpus donné.
     *
     * @param corpus Le corpus à analyser.
     * @return Le nombre total de mots présents dans tous les documents du corpus.
     */
    @Override
    public int calculer(Corpus corpus) {
        int count = 0;

        // Parcourt chaque document du corpus
        for (Document doc : corpus) {
            count += doc.size(); // Ajoute la taille (nombre de mots) de chaque document
        }

        return count; // Retourne le total des mots
    }
}