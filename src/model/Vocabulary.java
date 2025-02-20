package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * La classe `Vocabulary` est utilisée pour gérer le vocabulaire d'un corpus.
 * Elle contient une liste de mots avec leurs identifiants (`wordToId`) ainsi qu'une liste de mots vides (`stopList`).
 * Cette classe suit le pattern Singleton pour garantir une instance unique.
 */
public class Vocabulary {
    private static Vocabulary instance = null; // Instance unique de Vocabulary
    private HashMap<Mot, Integer> wordToId; // Map associant chaque mot à un identifiant unique
    private HashSet<Mot> stopList; // Ensemble des mots vides (stop words)

    /**
     * Constructeur privé de la classe `Vocabulary`.
     * Initialise les structures de données `wordToId` et `stopList`,
     * et remplit `stopList` à partir d'un fichier.
     */
    private Vocabulary() {
        wordToId = new HashMap<>();
        stopList = new HashSet<>();
        fillStopListFromFile("stopList.txt"); // Charge la stopList depuis un fichier
    }

    /**
     * Retourne l'instance unique de la classe `Vocabulary`.
     * Si elle n'existe pas encore, elle est créée.
     *
     * @return L'instance unique de Vocabulary.
     */
    public static Vocabulary getInstance() {
        if (instance == null) {
            instance = new Vocabulary();
        }
        return instance;
    }

    /**
     * Ajoute un mot à la liste des mots vides (`stopList`).
     *
     * @param mot Le mot à ajouter comme un objet `Mot`.
     */
    public void addStopWord(Mot mot) {
        stopList.add(mot);
    }

    /**
     * Ajoute un mot au vocabulaire en lui assignant un identifiant unique.
     *
     * @param mot Le mot à ajouter comme un objet `Mot`.
     */
    public void addWord(Mot mot) {
        wordToId.put(mot, wordToId.size());
    }

    /**
     * Retourne l'identifiant associé à un mot donné.
     *
     * @param mot Le mot pour lequel récupérer l'identifiant.
     * @return L'identifiant du mot ou `null` si le mot n'existe pas dans le vocabulaire.
     */
    public Integer getWordId(Mot mot) {
        return wordToId.get(mot);
    }

    /**
     * Retourne la taille du vocabulaire, c'est-à-dire le nombre de mots.
     *
     * @return Nombre total de mots dans le vocabulaire.
     */
    public int size() {
        return wordToId.size();
    }

    /**
     * Retourne l'ensemble des mots vides (`stopList`).
     *
     * @return Un HashSet contenant les mots vides.
     */
    public HashSet<Mot> getStopList() {
        return stopList;
    }

    /**
     * Affiche les mots du vocabulaire avec leurs identifiants.
     *
     * @param maxEntries Nombre maximum de mots à afficher.
     */
    public void afficheWordToId(int maxEntries) {
        System.out.println("Mots dans le Vocabulaire (wordToId):");
        int count = 0;
        for (Map.Entry<Mot, Integer> entry : wordToId.entrySet()) {
            System.out.println(entry.getKey().getMot() + " -> " + entry.getValue());
            count++;
            if (count >= maxEntries) break; // Limite l'affichage au nombre spécifié
        }
    }

    /**
     * Affiche les mots vides présents dans la stopList.
     *
     * @param maxEntries Nombre maximum de mots à afficher.
     */
    public void afficheStopList(int maxEntries) {
        System.out.println("Mots vides dans le Vocabulaire (stopList):");
        int count = 0;
        for (Mot mot : stopList) {
            System.out.println(mot.getMot());
            count++;
            if (count >= maxEntries) break; // Limite l'affichage au nombre spécifié
        }
    }

    /**
     * Remplit la stopList en lisant les mots depuis un fichier.
     *
     * @param filePath Chemin du fichier contenant les mots vides.
     */
    public void fillStopListFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) { // Lit chaque ligne du fichier
                line = line.trim(); // Supprime les espaces au début et à la fin
                if (!line.isEmpty()) { // Vérifie que la ligne n'est pas vide
                    addStopWord(new Mot(line)); // Ajoute le mot à la stopList
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier stopList: " + e.getMessage());
        }
    }
}