import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
/**
 * TeluguDictionary.java
 * 
 * Loads Telugu words and their frequencies from txt
 */
public class TeluguDictionary {
    private final Map<String, Integer> freq = new HashMap<>();
    public TeluguDictionary(String filePath) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath), StandardCharsets.UTF_8)) {
            String line ; 
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    try {
                        freq.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                    } catch (NumberFormatException e) {
                        // skip bad lines
                    }
                }
            }
        }
        System.out.println("Loaded Telugu dictionary: " + freq.size() + " words");
    }

    public boolean isValid(String word) {
        return freq.containsKey(word);
    }

    public Set<String> getWords() {
        return freq.keySet();
    }

    public int getFreq(String word) {
        return freq.getOrDefault(word, 0);
    }
}
