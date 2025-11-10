// SentimentAnalysis.java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SentimentAnalysis {

    private final HashSet<String> positiveWords;
    private final  HashSet<String> negativeWords;
    private final HashMap<String, Double> scoredLexicon;

    public SentimentAnalysis(
        HashSet<String> posWords, 
        HashSet<String> negWords, 
        HashMap<String, Double> scoredLex) {
        
        this.positiveWords = posWords;
        this.negativeWords = negWords;
        this.scoredLexicon = scoredLex;
    }

    public double analyzeScoredSentiment(ArrayList<String> words) {
        double totalScore = 0.0;
        int scoredWordsCount = 0;

        for (String word : words) {
            if (scoredLexicon.containsKey(word)) {
                totalScore += scoredLexicon.get(word);
                scoredWordsCount++;
            }
        }
        
        if (scoredWordsCount == 0) {
            return 0.0; 
        }
        
        return totalScore / scoredWordsCount;
    }

    public String analyzeSimpleSentiment(ArrayList<String> words) {
        int posCount = 0;
        int negCount = 0;

        for (String word : words) {
            if (positiveWords.contains(word)) {
                posCount++;
            } else if (negativeWords.contains(word)) {
                negCount++;
            }
        }

        String netSentiment = (posCount > negCount) 
            ? "Positive" 
            : ((negCount > posCount) ? "Negative" : "Neutral");

        return String.format(
            "Positive Word Count: %d, Negative Word Count: %d, Net Sentiment: %s", 
            posCount, negCount, netSentiment
        );
    }
}
