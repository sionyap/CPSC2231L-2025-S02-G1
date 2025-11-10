import java.util.ArrayList;

public class ArticleStatistics {

    /**
     * Counts word frequencies, ranks them, and prints statistics.
     * @param filteredWords The list of words after stop word removal.
     * @return The list of ranked WordCount objects.
     */
    public static ArrayList<WordCount> generateStatsAndRank(ArrayList<String> filteredWords) {
        ArrayList<WordCount> wordCountList = new ArrayList<>();

        // 1. Count word frequency (Linear Search/Counting logic)
        for (String word : filteredWords) {
            boolean found = false;
            for (WordCount wc : wordCountList) {
                if (wc.word.equals(word)) {
                    wc.count++;
                    found = true;
                    break;
                }
            }
            if (!found) {
                wordCountList.add(new WordCount(word, 1));
            }
        }
        
        // 2. Calculate Basic Statistics
        int totalWords = filteredWords.size();
        int uniqueWords = wordCountList.size();
        System.out.println("\n--- Basic Statistics (Milestone 1) ---");
        System.out.println("Total words (after stop word removal): " + totalWords);
        System.out.println("Number of unique words: " + uniqueWords);

        // 3. Sort (Rank) the wordCountList by count in descending order using Bubble Sort
        for (int i = 0; i < wordCountList.size() - 1; i++) {
            for (int j = 0; j < wordCountList.size() - i - 1; j++) {
                if (wordCountList.get(j).count < wordCountList.get(j + 1).count) {
                    // Swap WordCount objects
                    WordCount temp = wordCountList.get(j);
                    wordCountList.set(j, wordCountList.get(j + 1));
                    wordCountList.set(j + 1, temp);
                }
            }
        }

        // 4. Print the words ranked by frequency (Top 20)
        System.out.println("\n--- Word Ranking by Frequency (Top 20) ---");
        int maxPrint = Math.min(20, wordCountList.size());
        for (int i = 0; i < maxPrint; i++) {
            WordCount wc = wordCountList.get(i);
            System.out.println(String.format("%2d. %-15s: %d", (i + 1), wc.word, wc.count));
        }

        return wordCountList;
    }
}
