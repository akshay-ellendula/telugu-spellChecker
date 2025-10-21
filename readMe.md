# Telugu Spell Checker & Corrector

A high-performance Java utility designed to identify and correct misspelled words in Telugu text. This project provides an effective solution based on established phonetic and statistical algorithms.

***

## Technical Implementation & Features

This program meets its objectives through a combination of data structures and algorithms:

* **Misspelling Detection**: A `HashMap` is used to store an in-memory dictionary, allowing for constant-time $O(1)$ average lookups to validate words.

* **Candidate Generation**: The **Damerau-Levenshtein distance** algorithm is implemented to find words that are a minimum "edit distance" away from a misspelled word. This algorithm accounts for the four fundamental edit operations:
    1.  **Insertion** (adding a character)
    2.  **Deletion** (removing a character)
    3.  **Substitution** (replacing a character)
    4.  **Transposition** (swapping two adjacent characters)

* **Semantic Ranking**: To ensure the most probable correction is suggested, candidates are ranked using a hybrid scoring model. The formula effectively prioritizes words that are both phonetically similar and statistically common:
    $$\text{Rank} = (\text{Edit Distance}) - \log(1 + \text{Word Frequency})$$
    This ensures that a common word with a slightly higher edit distance might be ranked higher than a rare word with a lower edit distance, leading to more contextually relevant corrections.

* **Automated Correction & Logging**: The program automatically replaces a misspelled word with the top-ranked candidate and logs all changes and suggestion lists for review.

***

## Project Structure 

### Core Java Files

* **`Main.java`**: The main driver for the spell-checking application.
  - Reads input Telugu text from `input.txt`
  - Identifies misspellings using dictionary lookup
  - Generates correction suggestions for misspelled words
  - Writes corrected text to `output.txt` and logs to `output_log.txt`

* **`SpellCorrector.java`**: Core logic for calculating edit distance and ranking candidates.
  - Implements Damerau-Levenshtein distance algorithm
  - Generates ranked suggestions based on edit distance and frequency
  - Provides `suggest(String word, int limit)` method for getting top corrections

* **`TeluguDictionary.java`**: Handles loading and managing the dictionary data.
  - Loads word-frequency pairs from `telugu_dictionary.txt`
  - Provides `isValid(String word)` for dictionary lookup
  - Manages word frequency data for ranking

* **`CorpusProcessor.java`**: Reads a raw text corpus and generates a word frequency dictionary.
  - Processes `telugu_corpus.txt` to count word frequencies
  - Cleans text by removing punctuation
  - Generates `telugu_dictionary.txt` in CSV format (word,frequency)

### Data Files

* **`telugu_corpus.txt`**: A large text file containing Telugu text used to build the frequency dictionary
                         **https://drive.google.com/drive/folders/1C48-3SHWYRYA9mEGIXxJycT8t5F1-TIA?usp=sharing**
* **`telugu_dictionary.txt`**: Generated dictionary file containing word-frequency pairs in CSV format.
* **`input.txt`**: The sample text file you want to spell-check (one word per line).
* **`output.txt`**: The corrected version of the text from `input.txt`.
* **`output_log.txt`**: A log detailing each misspelled word and the suggestions generated for it.

***

## How to Run 

### Step 1: Prerequisites & Setup

1. Ensure you have a Java Development Kit (JDK) 8 or higher installed.
2. Place all `.java` files in a single directory.
3. Ensure you have `telugu_corpus.txt` (with your Telugu text) and `input.txt` (with the text to be checked).

### Step 2: Compile

Open a terminal in your project directory and compile all the Java source files:
```bash
javac *.java
```

### Step 3: Create the Dictionary

Run the `CorpusProcessor` to read your corpus and create the dictionary file:
```bash
java CorpusProcessor
```
This will generate `telugu_dictionary.txt` with word-frequency pairs.

### Step 4: Execute the Spell Checker

Run the main spell checker:
```bash
java Main
```

***

## Algorithm Details 

### Edit Distance Calculation

The spell checker uses the **Damerau-Levenshtein distance** algorithm with the following operations:

1. **Insertion**: Adding a character (cost = 1)
2. **Deletion**: Removing a character (cost = 1)  
3. **Substitution**: Replacing a character (cost = 1)
4. **Transposition**: Swapping two adjacent characters (cost = 1)

### Ranking Formula

Candidates are ranked using the formula:
```
Rank = Edit Distance - log(1 + Word Frequency)
```

Where:
- **Lower rank values** indicate better matches
- **Edit Distance** is the minimum number of operations needed
- **Word Frequency** is the occurrence count from the corpus

###  Results depend on the corpus you use.