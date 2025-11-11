package TextAnalysis;

import java.util.*;

public class SentimentAnalysis {

    private HashSet<String> positive;
    private HashSet<String> negative;
    private HashMap<String, Double> lexicon;

    public SentimentAnalysis(HashSet<String> positive,
                             HashSet<String> negative,
                             HashMap<String, Double> lexicon) {

        this.positive = positive;
        this.negative = negative;
        this.lexicon = lexicon;
    }

    public int simple(ArrayList<String> words) {
        int score = 0;
        for (String w : words) {
            if (positive.contains(w)) score++;
            if (negative.contains(w)) score--;
        }
        return score;
    }

    public double scoredAvg(ArrayList<String> words) {

        double total = 0;
        int count = 0;

        for (String w : words) {
            if (lexicon.containsKey(w)) {
                total += lexicon.get(w);
                count++;
            }
        }

        return (count == 0) ? 0 : total / count;
    }
}
