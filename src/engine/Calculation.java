package engine;

import model.Document;
import model.DataSets;
import model.Vocabulary;
import model.Mot;
import model.Taille;

import exceptions.MoteurRechercheException;
import exceptions.Bm25Exception;
import exceptions.TfIdfException;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Classe abstraite `Calculation` servant de base pour l'implémentation
 * des modèles de calcul de scores (ex. BM25, TfIdf).
 * Elle fournit des méthodes communes pour traiter les corpus, calculer
 * les fréquences des termes, et évaluer les requêtes.
 */
abstract class Calculation {
    HashMap<Document, double[]> tf; // Carte des documents et leurs vecteurs de fréquence des termes
    double[] idf; // Tableau contenant les valeurs IDF (Inverse Document Frequency) des termes
    int TotalNumberDoc; // Nombre total de documents dans le corpus

    /**
     * Constructeur par défaut. Initialise la map des fréquences des termes (TF).
     */
    public Calculation() {
        tf = new HashMap<>();
    }

    /**
     * Met à jour le vocabulaire en parcourant un corpus précédent.
     * Ajoute des mots en minuscules au vocabulaire.
     *
     * @param corpus Le corpus à traiter.
     */
    public void vocabulairePrécéd(Corpus corpus) {
        Vocabulary vocab = Vocabulary.getInstance();
        for (Document doc : corpus) {
            for (Mot mot : doc) {
                if (vocab.getWordId(mot) == null) {
                    vocab.addWord(mot);
                }
            }
        }
    }

    /**
     * Met à jour le vocabulaire en excluant les mots vides (stop words).
     *
     * @param corpus Le corpus à traiter.
     */
    public void vocabulaire(Corpus corpus) {
        Vocabulary vocab = Vocabulary.getInstance();
        HashSet<Mot> stopList = vocab.getStopList(); // Liste des mots vides

        for (Document doc : corpus) {
            for (Mot mot : doc) {
                if (!stopList.contains(mot) && vocab.getWordId(mot) == null) {
                    vocab.addWord(mot);
                }
            }
        }
    }

    /**
     * Traite un corpus pour calculer les valeurs TF-IDF.
     *
     * @param corpus Le corpus à traiter.
     * @return L'instance actuelle (this) après traitement.
     * @throws TfIdfException Si le corpus est nul ou vide.
     */
    public Calculation processCorpus(Corpus corpus) throws TfIdfException {
        if (corpus == null || corpus.isEmpty()) {
            throw new TfIdfException("Le corpus est nul ou ne contient aucun document.");
        }

        // Met à jour le vocabulaire
        vocabulaire(corpus);

        Vocabulary vocab = Vocabulary.getInstance();
        int vocabSize = vocab.size(); // Taille du vocabulaire
        if (idf == null) {
            idf = new double[vocabSize]; // Initialize if not already created
        } else if (idf.length != vocabSize) {
            double[] oldIdf = idf;
            idf = new double[vocabSize];
            System.arraycopy(oldIdf, 0, idf, 0, Math.min(oldIdf.length, idf.length));
        }        
        
        int[] docFreq = new int[vocabSize]; // Fréquence des documents pour chaque terme

        // Met à jour les vecteurs existants pour correspondre à la taille du vocabulaire
        for (Document existingDoc : tf.keySet()) {
            double[] existingVector = tf.get(existingDoc);
            if (existingVector.length < vocabSize) {
                double[] updatedVector = new double[vocabSize];
                System.arraycopy(existingVector, 0, updatedVector, 0, existingVector.length);
                tf.put(existingDoc, updatedVector);
            }
        }

        // Traite chaque document dans le corpus
        for (Document doc : corpus) {
            double[] tfVector = new double[vocabSize];
            HashMap<Integer, Integer> termCounts = new HashMap<>();

            // Compte les fréquences des termes
            for (Mot mot : doc) {
                Integer wordId = vocab.getWordId(mot);
                termCounts.put(wordId, termCounts.getOrDefault(wordId, 0) + 1);
            }

            // Trouve la fréquence maximale pour la normalisation
            int maxTermCount = termCounts.values().stream().max(Integer::compare).orElse(1);

            // Calcule les fréquences normalisées et les fréquences de documents
            for (Integer wordId : termCounts.keySet()) {
                int count = termCounts.get(wordId);
                tfVector[wordId] = 0.5 + 0.5 * ((double) count / maxTermCount);
                docFreq[wordId]++;
            }

            tf.put(doc, tfVector); // Ajoute le vecteur TF au document
        }

        // Calcule les valeurs IDF
        int totalDocs = tf.size();
        for (int i = 0; i < vocabSize; i++) {
            idf[i] = Math.log(((double) totalDocs - docFreq[i] + 0.5) / (0.5 + docFreq[i])); // Lissage
        }

        return this;
    }

    /**
     * Traite une requête utilisateur et affiche les documents les plus pertinents.
     *
     * @param requête La requête de l'utilisateur.
     * @param maxDoc  Le nombre maximum de documents à afficher.
     * @throws Bm25Exception Si la requête est nulle ou vide.
     */
    public void processQuery(String requête, int maxDoc) throws Bm25Exception {
        if (requête == null || requête.isEmpty()) {
            throw new Bm25Exception("La requête de l'utilisateur est nulle ou vide.");
        }
        double[] queryVector = features(requête);
        HashMap<Document, Double> scoresDocuments = evaluate(queryVector);

        // Trie les scores par pertinence et affiche les meilleurs résultats
        scoresDocuments.entrySet().stream()
                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                .limit(maxDoc)
                .forEach(entry -> {
                    System.out.println("Document: " + entry.getKey().getTitre() + ", Score: " + entry.getValue());
                });
    }

    /**
     * Extrait les caractéristiques de la requête (ancienne version).
     *
     * @param query La requête utilisateur.
     * @return Un vecteur de fréquences des termes pour la requête.
     */
    protected double[] featuresPrécéd(String query) {
        Vocabulary vocab = Vocabulary.getInstance();
        int vocabSize = vocab.size();
        double[] queryVector = new double[vocabSize];

        String[] words = query.toLowerCase().split("\\s+");
        for (String word : words) {
            Mot mot = new Mot(word);
            Integer wordId = vocab.getWordId(mot);
            if (wordId != null) {
                queryVector[wordId] += 1.0;
            }
        }
        return queryVector;
    }

    /**
     * Extrait les caractéristiques de la requête en excluant les mots vides.
     *
     * @param query La requête utilisateur.
     * @return Un vecteur de fréquences des termes pour la requête.
     */
    protected double[] features(String query) {
        Vocabulary vocab = Vocabulary.getInstance();
        HashSet<Mot> stopList = vocab.getStopList();
        int vocabSize = vocab.size();
        double[] queryVector = new double[vocabSize];

        String[] words = query.toLowerCase().split("\\s+");
        for (String word : words) {
            Mot mot = new Mot(word);
            if (!stopList.contains(mot)) {
                Integer wordId = vocab.getWordId(mot);
                if (wordId != null) {
                    queryVector[wordId] += 1.0;
                }
            }
        }
        return queryVector;
    }

    /**
     * Méthode abstraite pour évaluer les scores des documents.
     *
     * @param vectReq Le vecteur de requête.
     * @return Une map associant les documents à leurs scores.
     */
    abstract protected HashMap<Document, Double> evaluate(double[] vectReq);
}