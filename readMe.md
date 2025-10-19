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

## Project Structure 📁

* `CorpusProcessor.java`: Reads a raw text corpus and generates a word frequency dictionary.
* `Main.java`: The main driver for the spell-checking application.
* `SpellCorrector.java`: Core logic for calculating edit distance and ranking candidates.
* `TeluguDictionary.java`: Handles loading and managing the dictionary data.
* `telugu_corpus.txt`: **https://drive.google.com/drive/folders/1C48-3SHWYRYA9mEGIXxJycT8t5F1-TIA?usp=sharing**
                        A large text file of Telugu words to build the dictionary.
* `input.txt`: **(You provide)** The sample text file you want to spell-check.
***

## How to Run 🚀

### Step 1: Prerequisites & Setup

1.  Ensure you have a Java Development Kit (JDK) 8 or higher installed.
2.  Place all `.java` files in a single directory.
3.  In the same directory, create `telugu_corpus.txt` (with your Telugu text) and `input.txt` (with the text to be checked).

### Step 2: Compile

Open a terminal in your project directory and compile all the Java source files:
```bash
javac *.java
```
### Step 3: Create the Dictionary
 Run the` CorpusProcessor` to read your corpus and create the dictionary file. This command will generate `telugu_corpus.txt`.
```bash
java CorpusProcessor  
``` 
### Step 4: Execute the program:
```bash
java Main
``` 
## Output Files 📝

After running, the program will generate the following files in the same directory:

* **`output.txt`**: The corrected version of the text from **`input.txt`**.
* **`output_log.txt`**: A log detailing each misspelled word and the suggestions generated for it.
* **`telugu_dictionary.txt`**: A clean, indexed copy of the dictionary that was loaded into memory.
