package TextAnalysis;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class ResourceManager {

    private HashSet<String> positive;
    private HashSet<String> negative;
    private HashSet<String> stopwords;
    private HashMap<String, Double> lexicon;

    public ResourceManager() throws IOException {

        positive = loadSet("C:\\Users\\NJP20\\OneDrive\\Desktop\\Classes\\Programming Workshop Lab\\TextAnalysisFinalProject\\src\\TextAnalysis\\AdditionalFiles\\positive-words (1).txt");
        negative = loadSet("C:\\Users\\NJP20\\OneDrive\\Desktop\\Classes\\Programming Workshop Lab\\TextAnalysisFinalProject\\src\\TextAnalysis\\AdditionalFiles\\negative-words (1).txt");
        stopwords = loadSet("C:\\Users\\NJP20\\OneDrive\\Desktop\\Classes\\Programming Workshop Lab\\TextAnalysisFinalProject\\src\\TextAnalysis\\AdditionalFiles\\stopwords (1).txt");
        lexicon = loadLexicon("C:\\Users\\NJP20\\OneDrive\\Desktop\\Classes\\Programming Workshop Lab\\TextAnalysisFinalProject\\src\\TextAnalysis\\AdditionalFiles\\lexicon_scores (1).txt");
    }

    private HashSet<String> loadSet(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.ISO_8859_1);
        HashSet<String> set = new HashSet<>();
        for (String line : lines) {
            if (line == null) continue;
            line = stripBom(line).trim().toLowerCase();
            if (line.isEmpty()) continue;
            if (line.startsWith(";") || line.startsWith("#")) continue; 
            set.add(line);
        }
        return set;
    }

    private HashMap<String, Double> loadLexicon(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.ISO_8859_1);
        HashMap<String, Double> map = new HashMap<>();

        for (String raw : lines) {
            if (raw == null) continue;
            String line = stripBom(raw).trim();
            if (line.isEmpty()) continue;
            if (line.startsWith(";") || line.startsWith("#")) continue;

            String[] parts = line.split("[\\t, ]+");
            if (parts.length < 2) continue;

            String word = parts[0].toLowerCase();

            Double score = null;
            for (int i = parts.length - 1; i >= 1; i--) {
                String tok = parts[i].trim();
                if (tok.isEmpty()) continue;
                try {
                    score = Double.parseDouble(tok);
                    break;
                } catch (NumberFormatException ignored) {
                }
            }
            if (score == null) {
                continue;
            }

            map.put(word, score);
        }
        return map;
    }

    private String stripBom(String s) {
        if (s.length() > 0 && s.charAt(0) == '\uFEFF') {
            return s.substring(1);
        }
        return s;
    }

    public HashSet<String> getPositiveWords() { return positive; }
    public HashSet<String> getNegativeWords() { return negative; }
    public HashSet<String> getStopWords() { return stopwords; }
    public HashMap<String, Double> getLexiconScores() { return lexicon; }
}