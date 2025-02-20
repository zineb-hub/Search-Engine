package model;

import engine.Corpus;

/**
 * La classe `TailleDocument` implémente l'interface `Taille`.
 * Elle est utilisée pour calculer le nombre total de documents dans un corpus.
 */
public class TailleDocument implements Taille {

    /**
     * Calcule le nombre total de documents présents dans le corpus.
     *
     * @param corpus Le corpus à analyser.
     * @return Le nombre total de documents dans le corpus.
     */
    @Override
    public int calculer(Corpus corpus) {
        return corpus.size(); // Retourne le nombre d'éléments dans le corpus
    }
}