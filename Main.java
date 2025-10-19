import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
/**
 * Main.java — Telugu Spell Checker Driver
 * 
 * Reads input Telugu text, identifies misspellings,
 * ranks correction candidates, and writes the corrected text
 * and candidate sets to output files.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        TeluguDictionary dict = new TeluguDictionary("telugu_dictionary.txt");
        SpellCorrector corrector = new SpellCorrector(dict);
        // Read Telugu text file
        Path inputPath = Paths.get("input.txt");
        Path outputPath = Paths.get("output.txt");
        Path logPath = Paths.get("corrections_log.txt");

        if (!Files.exists(inputPath)) {
            System.err.println("❌ input.txt not found.");
            return;
        }

        List<String> lines = Files.readAllLines(inputPath, StandardCharsets.UTF_8);
        List<String> correctedLines = new ArrayList<>();
        List<String> log = new ArrayList<>();

        
        Map<String, List<String>> candidateMemory = new HashMap<>();

        for (String line : lines) {
            StringBuilder corrected = new StringBuilder();
            String[] words = line.trim().split("\\s+");

            for (String w : words) {
                if (dict.isValid(w)) {
                    corrected.append(w).append(" ");
                } else {
                    List<String> sugg = corrector.suggest(w, 3);
                    if (!sugg.isEmpty()) {
                        corrected.append(sugg.get(0)).append(" ");
                        log.add(w + " -> " + sugg);
                        candidateMemory.put(w, sugg);
                    } else {
                        corrected.append(w).append(" ");
                        log.add(w + " -> (no suggestion)");
                    }
                }
            }
            correctedLines.add(corrected.toString().trim());
        }

        Files.write(outputPath, correctedLines);
        Files.write(logPath, log);

        System.out.println("Spell checking complete!");
        System.out.println(" Output: " + outputPath.toAbsolutePath());
        System.out.println(" Log: " + logPath.toAbsolutePath());

    }
}