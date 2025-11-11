package TextAnalysis;

import java.util.*;

public class TextProcessor {

    private HashSet<String> stopwords;

    public TextProcessor(HashSet<String> stopwords) {
        this.stopwords = stopwords;
    }

    public ArrayList<String> tokenize(String text) {

        ArrayList<String> tokens = new ArrayList<>();
        if (text == null || text.isBlank()) return tokens;

        text = text.toLowerCase()
                   .replaceAll("[“”]", "\"")
                   .replaceAll("[‘’]", "'");

        text = text.replaceAll("[^a-z0-9'-]+", " ");

        String[] parts = text.split("\\s+");

        for (String w : parts) {
            if (w.isBlank()) continue;

            w = w.replaceAll("^[^a-z0-9]+|[^a-z0-9]+$", "");

            if (w.length() < 2) continue;

            if (stopwords.contains(w)) continue;

            tokens.add(w);
        }

        return tokens;
    }
}
