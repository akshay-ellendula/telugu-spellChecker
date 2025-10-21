import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
/**
 * CorpusProcessor.java
 *
 * Reads a raw Telugu text corpus file, counts the frequency of each unique word,
 * and writes the results to a Text file suitable for the Telugu Spell Checker.
 */
public class CorpusProcessor {

    public static void main(String[] args) {
        String corpusFilePath = "telugu_corpus.txt";
        String outputFilePath = "telugu_dictionary.txt";
        try {
            Map<String, Integer> wordFrequencies = countFrequencies(corpusFilePath);

            writeFrequenciesToText(wordFrequencies, outputFilePath);

            System.out.println("successfully created dictionary with file name: " + outputFilePath);
            System.out.println("total unique words found :- " + wordFrequencies.size());

        } catch (IOException e) {
            System.err.println("An error occurred during file processing.");
            e.printStackTrace();
        }
    }

    /*
     * Reads a text file and returns a map of words to their frequencies
     */
    private static Map<String, Integer> countFrequencies(String filePath) throws IOException {
        Map<String, Integer> frequencyMap = new HashMap<>();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] words = line.trim().split("\\s+");

                for (String word : words) {
                    String cleanedWord = word.replaceAll("[.,!?।\"]", "");
                    if (!cleanedWord.isEmpty()) {
                        frequencyMap.put(cleanedWord, frequencyMap.getOrDefault(cleanedWord, 0) + 1);
                    }
                }
            }
        }
        return frequencyMap;
    }
    /**
     * Writes the word frequency map to Text File
     */
    private static void writeFrequenciesToText(Map<String, Integer> frequencyMap, String filePath) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath), StandardCharsets.UTF_8)) {
            for (Map.Entry<String, Integer> entry : frequencyMap.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        }
    }
}