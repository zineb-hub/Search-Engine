package engine;
import model.Document;
 
import java.util.HashMap;

public class TfIdf extends Calculation {

    public TfIdf(){
        super();
    }

    protected HashMap<Document, Double> evaluate(double[] vectReq) {
        HashMap<Document, Double> scoresDocuments = new HashMap<>();

        for (Document doc : tf.keySet()) {
            double[] docVector = tf.get(doc);
            double score = cosineSimilarity(vectReq, docVector);
            scoresDocuments.put(doc, score);
        }
        return scoresDocuments;
    }

    private double[] TfIdfCalculer(double[] docVector) {
        double[] tfIdfVector = new double[docVector.length];
        for (int i = 0; i < docVector.length; i++) {
            tfIdfVector[i] = docVector[i] * idf[i]; // Multiply term frequency by IDF
        }
        return tfIdfVector;
    }

    private double cosineSimilarity(double[] vec1, double[] vec2) {
        double dotProduct = 0.0;
        double normVec1 = 0.0;
        double normVec2 = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            double v1 = vec1[i] * idf[i];
            double v2 = vec2[i] * idf[i];
            dotProduct += v1 * v2;
            normVec1 += v1 * v1;
            normVec2 += v2 * v2;
        }
        return dotProduct / (Math.sqrt(normVec1) * Math.sqrt(normVec2) + 1e-10);
    }
}
