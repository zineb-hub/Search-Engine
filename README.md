# 📚 Project Documentation - Search Engine

Welcome to the documentation of our **search engine project** based on **TF-IDF** and **BM25**. This project is developed in **Java** and provides a robust solution for processing user queries and evaluating document relevance within a textual corpus.

---

## 1. Introduction

This project is a search engine that leverages **TF-IDF** and **BM25** models to analyze textual document corpora.  
It offers:
- **Data preprocessing** (converting words to lowercase, stemming with Porter Stemmer, handling stop words).  
- **Score computation** using **TF-IDF** and **BM25** models.  
- **Exception handling** to manage corpus and query errors efficiently.

---

## 2. Team Members

- **MEFTAH Zineb**  
- **MEDJBER Syphax**
- 
---

## 3. General Structure

### Main Modules

1. **Model**  
   - Contains core classes such as **Document**, **Word**, **Vocabulary**, etc.
2. **Engine**  
   - Provides classes for processing corpora and computing scores using **TF-IDF** and **BM25**.
3. **Exceptions**  
   - Defines custom exceptions to handle project-specific errors (**CorpusException, TfIdfException, Bm25Exception**).

### Summary of Features

- **Data Preprocessing**  
  - Converts words to lowercase.  
  - Stemming using the **Porter Stemmer** algorithm.  
  - Stop words filtering (loaded from `stopList.txt`).
  
- **Score Computation**  
  - Implementation of **TF-IDF** and **BM25** models.  
  - Adjustable parameters: **k1** and **b**.
  
- **Exception Handling**  
  - Dedicated exception classes for handling invalid corpus or queries.

---

## 4. Key Classes and Functions

### 4.1. Document Management
- **Word**  
  Represents a normalized word (lowercase + stemming) and implements `equals()` and `hashCode()`.
- **Document**  
  Represents a document containing a title and a list of words.  
  **Key Methods:**  
  - `putWord(String word)`: Adds a word to the document.  
  - `toString()`: Generates a textual representation of the document.

### 4.2. Corpus Management
- **Corpus**  
  Represents a collection of documents and provides:
  - **Reading text files** to load documents.
  - **Handling stop words** and **stemming** for better search efficiency.
  
  **Key Methods:**  
  - `addDocument(String title, String content)`: Adds a document after preprocessing.  
  - `getFeatures(Calculation model)`: Applies a computational model to the corpus.

### 4.3. Calculation Models
- **Calculation (Abstract Class)**  
  The base class for **TF-IDF** and **BM25** models.  
  **Key Methods:**  
  - `processCorpus(Corpus corpus)`: Processes a corpus to compute term frequencies and IDF values.  
  - `processQuery(String query, int maxDocs)`: Processes a user query and returns the most relevant documents.
  
- **TfIdf**  
  Implements **TF-IDF** score computation.
  
- **Bm25**  
  Implements **BM25** score computation with tuning parameters **k1** and **b** for document length normalization.

### 4.4. Custom Exceptions
- **SearchEngineException**  
  The base class for all exceptions in the project.
- **CorpusException**, **TfIdfException**, **Bm25Exception**  
  Thrown when:
  - **Corpus is invalid** (null or empty).
  - **TF-IDF corpus is missing**.
  - **BM25 query is invalid**.

---

## 5. Key Concepts

### 5.1. Word Management
- **Normalization:** Converts words to lowercase.  
- **Stemming:** Uses the **Porter Stemmer** algorithm.  
- **Stop Word Handling:** Filters non-relevant words from `stopList.txt`.

### 5.2. Corpus Management
- Reads documents from **text files** (e.g., `WIKIPEDIA.txt`, `BOOK.txt`).  
- Uses **partial datasets** for testing due to large dataset sizes.

### 5.3. Score Computation
- **TF-IDF:** Based on term frequency and inverse document frequency.  
- **BM25:** Adjusts scores based on document length and term frequency.

### 5.4. Exception Handling
- Each exception provides a **descriptive error message** and displays its hierarchy using `toString()`.  
- Exceptions are thrown in cases like:
  - **Null or empty corpus.**  
  - **Invalid user query.**

---

## 6. Conclusion

This project implements a **powerful search engine** with:
1. **Efficient word and corpus management.**
2. **Score computation using TF-IDF and BM25.**
3. **Robust error handling through custom exceptions.**

The code is **modular, well-structured, and follows best Java practices**, making it ready for real-world **information retrieval applications**.

---

## 🙏 Acknowledgments

We extend our gratitude to everyone who contributed to this project and the users who trust us to improve their search experience.
