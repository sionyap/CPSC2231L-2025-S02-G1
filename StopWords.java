import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class StopWords {

    private static final List<String> STOP_LIST = Arrays.asList(
        "a", "about", "above", "after", "again", "against", "all", "am", "an", "and", 
        "any", "are", "aren't", "as", "at", "be", "because", "been", "before", "being", 
        "below", "between", "both", "but", "by", "can't", "cannot", "could", "couldn't", 
        "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", 
        "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", 
        "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here", 
        "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", 
        "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", 
        "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", 
        "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought", 
        "our", "ours", "ourselves", "out", "over", "own", "per", "pertaining", 
        "said", "same", "shan't", "she", "she'd", "she'll", "she's", "should", 
        "shouldn't", "so", "some", "such", "than", "that", "that's", "the", "their", 
        "theirs", "them", "themselves", "then", "there", "there's", "these", "they", 
        "they'd", "they'll", "they're", "they've", "this", "those", "through", 
        "to", "too", "under", "until", "up", "very", "was", "wasn't", "we", "we'd", 
        "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", 
        "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", 
        "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", 
        "you're", "you've", "your", "yours", "yourself", "yourselves"
    );

    /**
     * Filters the list of words by removing any word found in the STOP_LIST.
     * Modifies the input list in place.
     * @param words The list of words to be filtered.
     */
    public static void filterWords(ArrayList<String> words) {
        Iterator<String> iterator = words.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            // Check if the word is in the stop list
            if (STOP_LIST.contains(word)) {
                iterator.remove();
            }
        }
    }
}