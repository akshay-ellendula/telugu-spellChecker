import java.util.*;

/**
 * SpellCorrector.java
 * 
 * Generates ranked suggestions for misspelled Telugu words.
 * Uses Levenshtein edit distance (insertion, deletion, substitution, transposition)
 * combined with frequency ranking for semantic weighting.
 */
public class SpellCorrector {
    private final TeluguDictionary dict;

    public SpellCorrector(TeluguDictionary dict) {
        this.dict = dict;
    }

    // Compute edit distance (4 operations)
    private int editDistance(String a, String b) {
        int m = a.length();
        int n = b.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(Math.min(
                        dp[i - 1][j] + 1,        // deletion
                        dp[i][j - 1] + 1),       // insertion
                        dp[i - 1][j - 1] + cost  // substitution
                );

                // transposition
                if (i > 1 && j > 1 &&
                        a.charAt(i - 1) == b.charAt(j - 2) &&
                        a.charAt(i - 2) == b.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + cost);
                }
            }
        }
        return dp[m][n];
    }

    // Suggest probable corrections (ranked by distance + frequency)
    public List<String> suggest(String word, int limit) {
        List<Map.Entry<String, Double>> ranked = new ArrayList<>();

        for (String dictWord : dict.getWords()) {
            int dist = editDistance(word, dictWord);
            if (dist <= 2) {
                double rank = dist - Math.log(1 + dict.getFreq(dictWord));
                ranked.add(Map.entry(dictWord, rank));
            }
        }

        ranked.sort(Map.Entry.comparingByValue());
        List<String> suggestions = new ArrayList<>();
        for (int i = 0; i < Math.min(limit, ranked.size()); i++) {
            suggestions.add(ranked.get(i).getKey());
        }
        return suggestions;
    }
}
