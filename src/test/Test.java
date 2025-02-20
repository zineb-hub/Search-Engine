package test;
import engine.Corpus;
import model.Taille;
import model.TailleDocument;
import model.TailleMot;
import engine.Bm25;
import engine.TfIdf;
import model.DataSets;

import exceptions.CorpusException;
import exceptions.Bm25Exception;
import exceptions.TfIdfException;
import exceptions.MoteurRechercheException;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        try {
            // Créer des objets de corpus
            Corpus corpusWIKIPEDIA = new Corpus("WIKIPEDIA.txt", DataSets.WIKIPEDIA);
            Corpus corpusBOOKS = new Corpus("BOOK.txt", DataSets.BOOKS);

            //System.out.println("corpus WIKIPEDIA: " + corpusWIKIPEDIA.toString());
            //System.out.println("corpus Books: " + corpusBOOKS.toString());

            // Taille calculations
            TailleDocument TDoc = new TailleDocument();
            TailleMot TMot = new TailleMot();

            System.out.println("Taille Document dans le corpus WIKIPEDIA " + corpusWIKIPEDIA.getTitre() + " est: " + corpusWIKIPEDIA.taille(TDoc));
            System.out.println("Taille Mot dans le corpus WIKIPEDIA " + corpusWIKIPEDIA.getTitre() + " est: " + corpusWIKIPEDIA.taille(TMot));
            System.out.println("Taille Document dans le corpus Books " + corpusBOOKS.getTitre() + " est: " + corpusBOOKS.taille(TDoc));
            System.out.println("Taille Mot dans le corpus Books " + corpusBOOKS.getTitre() + " est: " + corpusBOOKS.taille(TMot));

            // Traitement avec TfIdf
            TfIdf tfidf = new TfIdf();
            corpusWIKIPEDIA.getFeatures(tfidf);
            corpusBOOKS.getFeatures(tfidf);

            // Requête utilisateur pour TfIdf
            Scanner scanner = new Scanner(System.in);
            System.out.println("Entrez votre requête pour Tfidf:");
            String queryTfidf = scanner.nextLine();
            tfidf.processQuery(queryTfidf, 5);

            // Traitement avec Bm25
            Bm25 bm25 = new Bm25();
            corpusWIKIPEDIA.getFeatures(bm25);
            corpusBOOKS.getFeatures(bm25);

            // Requête utilisateur pour BM25
            System.out.println("Entrez votre requête pour BM25:");
            String queryBm25 = scanner.nextLine();
            bm25.processQuery(queryBm25, 5);

            // Fermer le scanner une fois à la fin
            scanner.close();

        } catch (CorpusException e) {
            System.err.println(e); // Gère Corpus-related errors
            System.exit(-1);
        } catch (TfIdfException e) {
            System.err.println(e); // Gère TfIdf-related errors
            System.exit(-1);
        } catch (Bm25Exception e) {
            System.err.println(e); // Gère BM25-related errors
            System.exit(-1);
        } catch (Exception e) {
            // Catch-all pour les exceptions inattendues
            System.err.println("An unexpected error occurred:");
            e.printStackTrace();
            System.exit(-1); // quitter le programme 
        }
    }

}
