package TextAnalysis;

import java.util.*;

public class AppController {

    public void run() {
        try {
            ApiFetcher api = new ApiFetcher();
            ArrayList<ApiFetcher.ApiArticle> articles = api.fetchArticles();

            ResourceManager rm = new ResourceManager();

            SentimentAnalysis sa = new SentimentAnalysis(
                rm.getPositiveWords(),
                rm.getNegativeWords(),
                rm.getLexiconScores()
            );

           System.out.println("=== ANALYSIS ===");
System.out.println("Fetched " + articles.size() + " articles.");

for (ApiFetcher.ApiArticle art : articles) {
    String full = art.toString();

    TextProcessor tp = new TextProcessor(rm.getStopWords());
    ArrayList<String> words = tp.tokenize(full);

    System.out.println("Tokens (sample): " + 
        words.subList(0, Math.min(20, words.size())));

    int wordCount = words.size();
    HashSet<String> unique = new HashSet<>(words);

    HashMap<String, Integer> freq = new HashMap<>();
    for (String w : words) freq.put(w, freq.getOrDefault(w, 0) + 1);

    List<Map.Entry<String, Integer>> topWords =
            freq.entrySet()
                .stream()
                .sorted((a,b)->b.getValue() - a.getValue())
                .limit(10)
                .toList();

    double vocabRichness = (wordCount == 0)
            ? 0
            : (double) unique.size() / wordCount;

    int simpleScore = sa.simple(words);
    double lexScore = sa.scoredAvg(words);

    System.out.println("\n---------------------------------------------------");
    System.out.println("Title: " + art.getTitle());
    System.out.println("Author: " + getAuthorSafe(art));
    System.out.println("Word Count: " + wordCount);
    System.out.println("Unique Words: " + unique.size());
    System.out.printf("Vocabulary Richness: %.4f%n", vocabRichness);

    System.out.println("\nTop 10 Most Frequent Words:");
    for (var e : topWords) {
        System.out.println("  " + e.getKey() + ": " + e.getValue());
    }

    System.out.println("\nSentiment Analysis:");
    System.out.println("  Simple Score (pos-neg): " + simpleScore);
    System.out.printf("  Lexicon Average Score: %.4f%n", lexScore);
}

        } catch (Exception e) {
            e.printStackTrace();  
        }
    }

    private String getAuthorSafe(ApiFetcher.ApiArticle art) {
        if (art == null) return "N/A";
        try {
            java.lang.reflect.Method m = art.getClass().getMethod("getAuthor");
            Object o = m.invoke(art);
            return o == null ? "N/A" : String.valueOf(o);
        } catch (Exception e1) {
            try {
                java.lang.reflect.Field f = art.getClass().getDeclaredField("author");
                f.setAccessible(true);
                Object o = f.get(art);
                return o == null ? "N/A" : String.valueOf(o);
            } catch (Exception e2) {
                try {
                    java.lang.reflect.Method m2 = art.getClass().getMethod("getCreator");
                    Object o2 = m2.invoke(art);
                    return o2 == null ? "N/A" : String.valueOf(o2);
                } catch (Exception e3) {
                    return "N/A";
                }
            }
        }
    }
}
