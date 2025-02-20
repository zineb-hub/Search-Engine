package engine;

import model.Document;
import java.util.HashMap;

/**
 * La classe Bm25 implémente la fonction de ranking BM25.
 * Elle est utilisée pour calculer des scores de pertinence pour
 * les documents par rapport à une requête donnée.
 * Cette classe hérite de la classe Calculation.
 */
public class Bm25 extends Calculation {

    // Paramètres de réglage pour BM25
    private double k1 = 1.2; // Contrôle la saturation de la fréquence des termes
    private double b = 0.75; // Contrôle la normalisation de la longueur des documents
    private double avgDl; // Longueur moyenne des documents dans le corpus

    /**
     * Constructeur par défaut pour la classe Bm25.
     * Appelle le constructeur de la classe parente Calculation.
     */
    public Bm25() {
        super();
    }

    /**
     * Calcule la longueur moyenne des documents (avgDl) dans le corpus.
     * Cette valeur est utilisée dans le calcul des scores BM25.
     *
     * @param tf Une HashMap où chaque clé est un Document
     *           et chaque valeur est un vecteur de fréquence des termes
     *           pour ce document.
     */
    public void computeAvgDl(HashMap<Document, double[]> tf) {
        int totalLength = 0;

        // Somme des longueurs de tous les documents
        for (Document doc : tf.keySet()) {
            totalLength += doc.size(); // Méthode standard de la classe Document
        }

        // Calcul de la longueur moyenne
        avgDl = (double) totalLength / tf.size();
    }

    /**
     * Évalue les scores BM25 pour un ensemble de documents à partir
     * d'un vecteur de requête.
     *
     * @param queryVector Un tableau de doubles représentant le vecteur de la requête.
     *                    Chaque élément correspond à un terme dans le vocabulaire.
     * @return Une HashMap où chaque clé est un Document et chaque valeur est
     *         son score de pertinence BM25.
     */
    protected HashMap<Document, Double> evaluate(double[] queryVector) {
        HashMap<Document, Double> scoresDocuments = new HashMap<>();

        // Vérifie que avgDl est calculé avant le scoring
        computeAvgDl(tf);

        // Parcourt tous les documents dans la map des fréquences des termes
        for (Document doc : tf.keySet()) {
            double[] docTermFreq = tf.get(doc); // Vecteur de fréquence des termes du document
            double score = 0.0;
            int docLength = doc.size(); // Longueur du document (nombre de termes)

            // Calcule le score BM25 pour chaque terme de la requête
            for (int i = 0; i < queryVector.length; i++) {
                if (queryVector[i] > 0) { // Vérifie si le terme apparaît dans la requête
                    double freq = docTermFreq[i]; // Fréquence du terme dans le document
                    if (freq > 0) {
                        // Calcul des composants du score BM25
                        double numerator = freq * (k1 + 1);
                        double denominator = freq + k1 * (1 - b + b * (double) docLength / avgDl);
                        score += idf[i] * (numerator / denominator); // Ajoute au score total
                    }
                }
            }

            // Associe le score BM25 au document
            scoresDocuments.put(doc, score);
        }

        // Retourne les scores BM25 pour tous les documents
        return scoresDocuments;
    }
}