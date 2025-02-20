package model;

/**
 * L'énumération `DataSets` représente les différents corpus de données utilisés dans le projet.
 * Chaque constante de l'énumération possède un nom descriptif.
 */
public enum DataSets {
    WIKIPEDIA("Wikipedia"), // données Wikipédia
    BOOKS("Books");         // données Livres

    private final String name; // Nom descriptif  de données

    /**
     * Constructeur de l'énumération `DataSets`.
     * Initialise le nom descriptif de chaque constante.
     *
     * @param name Le nom descriptif  de données.
     */
    DataSets(String name) {
        this.name = name;
    }

    /**
     * Retourne le nom descriptif de données.
     *
     * @return Le nom de corpus de données.
     */
    public String getName() {
        return name;
    }
}