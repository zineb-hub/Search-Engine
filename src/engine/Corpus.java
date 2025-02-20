package engine;

import model.Document;
import model.DataSets;
import model.Vocabulary;
import model.Mot;
import model.Taille;

import exceptions.MoteurRechercheException;
import exceptions.Bm25Exception;
import exceptions.CorpusException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Vector;

/**
 * La classe `Corpus` représente un ensemble de documents, étendu de `Vector<Document>`.
 * Elle permet de charger des documents à partir d'un fichier, de traiter leur contenu,
 * et d'exécuter des opérations comme le calcul des caractéristiques et des tailles.
 */
public class Corpus extends Vector<Document> {
    private String titre; // Titre du corpus
    private DataSets dataset; // Jeu de données associé
    private Calculation features; // Instance de TfIdf ou Bm25 pour le calcul des caractéristiques

    /**
     * Constructeur de la classe Corpus.
     *
     * @param cheminFichier Chemin du fichier contenant les documents.
     * @param dataset       Jeu de données associé au corpus.
     * @throws CorpusException Si le chemin du fichier est nul, vide, ou si une erreur de lecture se produit.
     */
    public Corpus(String cheminFichier, DataSets dataset) throws CorpusException {
        super();
        this.titre = cheminFichier;
        this.dataset = dataset;

        // Vérifie que le chemin du fichier n'est pas nul ou vide
        if (cheminFichier == null || cheminFichier.isEmpty()) {
            throw new CorpusException("Le chemin du fichier est nul ou vide. Impossible de créer le Corpus.");
        }

        // Lit le fichier et remplit le Vector<Document>
        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException e) {
            throw new CorpusException("Erreur lors de la lecture du fichier: " + cheminFichier + " - " + e.getMessage());
        }
    }

    /**
     * Analyse une ligne du fichier pour extraire le titre et le contenu.
     *
     * @param line Ligne à analyser au format "titre|||contenu".
     */
    private void parseLine(String line) {
        String[] parts = line.split("\\|\\|\\|"); // Séparateur spécifique au format
        if (parts.length >= 2) { // Vérifie que la ligne contient un titre et un contenu
            String title = parts[0].trim().toLowerCase(); // Convertit le titre en minuscules
            String content = parts[1].trim().toLowerCase(); // Convertit le contenu en minuscules
            addDocument(title, content); // Ajoute le document au Vector<Document>
        }
    }

    /**
     * Ajoute un document au corpus après traitement des mots.
     *
     * @param title   Titre du document.
     * @param content Contenu du document.
     */
    private void addDocument(String title, String content) {
        Vocabulary vocab = Vocabulary.getInstance(); // Instance unique du vocabulaire
        HashSet<Mot> stopList = vocab.getStopList(); // Liste des mots vides
        
        Document document = new Document(title); // Crée un document avec le titre
        String[] words = content.split("\\W+"); // Divise le contenu en mots (séparateurs non alphanumériques)
        for (String word : words) {
            if (!word.trim().isEmpty() && word.matches("[a-zA-Z]+")) { // Vérifie que le mot n'est pas vide
                word = word.trim(); // Supprime les espaces 
                Mot mot = new Mot(word);
                if (!stopList.contains(mot)) { // Exclut les mots présents dans la stopList
                    document.putMot(word); // Ajoute le mot au document
                }
            }
        }
        this.add(document); // Ajoute le document au corpus
    }

    /**
     * Définit le titre du corpus.
     *
     * @param Titre Le titre du corpus.
     */
    public void putTitre(String Titre) {
        this.titre = Titre;
    }

    /**
     * Retourne le titre du corpus.
     *
     * @return Le titre du corpus.
     */
    public String getTitre() {
        return this.titre;
    }

    /**
     * Ajoute un document au corpus.
     *
     * @param doc Le document à ajouter.
     */
    public void addDocument(Document doc) {
        this.add(doc);
    }

    /**
     * Génère une représentation en chaîne de caractères du corpus.
     *
     * @return Une chaîne décrivant le corpus et ses documents.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Titre du Corpus: ").append(titre).append("\n");
        sb.append("  Documents:\n");
        for (Document doc : this) {
            sb.append("    ").append(doc.toString()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Calcule une métrique spécifique pour le corpus.
     *
     * @param TailleChoice L'instance de Taille pour le calcul de la métrique.
     * @return La valeur calculée par TailleChoice.
     */
    public int taille(Taille TailleChoice) {
        return TailleChoice.calculer(this);
    }

    /**
     * Applique un modèle de calcul (ex. TfIdf ou Bm25) au corpus.
     *
     * @param model Le modèle de calcul à appliquer.
     * @return Le modèle après traitement.
     * @throws MoteurRechercheException Si une erreur se produit lors du traitement.
     */
    public Calculation getFeatures(Calculation model) throws MoteurRechercheException {
        model = model.processCorpus(this); // Traite le corpus avec le modèle
        this.features = model; // Stocke le modèle dans l'attribut features
        return model;
    }
}