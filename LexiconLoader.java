// LexiconLoader.java
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class LexiconLoader {

    public static HashSet<String> loadSimpleLexicon(String lexiconContent) {
        HashSet<String> lexicon = new HashSet<>();

        String contentLower = lexiconContent.toLowerCase();
        try (Scanner contentScanner = new Scanner(contentLower)) {
        
            while (contentScanner.hasNextLine()) {
                String line = contentScanner.nextLine().trim();

                if (!line.isEmpty() && !line.startsWith(";")) {
                    lexicon.add(line);
                }
            }
        }
        return lexicon;
    }

    public static HashMap<String, Double> loadScoredLexicon(String lexiconContent) {
        HashMap<String, Double> lexicon = new HashMap<>();
        String contentLower = lexiconContent.toLowerCase();
        try (Scanner contentScanner = new Scanner(contentLower)) {
        
            while (contentScanner.hasNextLine()) {
                String line = contentScanner.nextLine().trim();
                if (!line.isEmpty()) {

                    String[] parts = line.split("[\\s\\t]+"); 
                    if (parts.length == 2) {
                        try {
                            String word = parts[0];
                            double score = Double.parseDouble(parts[1]); 
                            lexicon.put(word, score);
                        } catch (NumberFormatException e) {
                        }
                    }
                }
            }
        }
        return lexicon;
    }
}