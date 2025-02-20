package model;

import engine.Corpus;

/**
 * L'interface `Taille` définit un contrat pour calculer une métrique
 * spécifique sur un corpus.
 * Les classes implémentant cette interface doivent fournir une méthode
 * `calculer` qui prend un corpus en entrée et retourne un entier.
 */
public interface Taille {

    /**
     * Calcule une métrique spécifique sur le corpus donné.
     *
     * @param: corpus Le corpus à analyser.
     * @return: La valeur entière correspondant à la métrique calculée.
     */
    int calculer(Corpus corpus);
}