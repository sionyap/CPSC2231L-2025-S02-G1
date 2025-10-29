// Main.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final String API_KEY = "fbcf0024f7514792a78ea28c6e1a71dc";

    private static final String NEWS_API_URL = 
        "https://newsapi.org/v2/top-headlines?country=us&category=general&pageSize=50&apiKey=" + API_KEY;
    
    private static final int MIN_PROXIED_WORD_COUNT = 50; 
    private static final int MAX_ARTICLES_TO_ANALYZE = 5;

    private static class Article {
        public String title;
        public String content; 
        public String source;

        public Article(String title, String content, String source) {
            this.title = title;
            this.content = content;
            this.source = source;
        }
    }
    
    private static ArrayList<Article> fetchNewsArticles() {
        ArrayList<Article> allArticles = new ArrayList<>();
        StringBuilder response = new StringBuilder();
        
        System.out.println("Attempting to fetch " + MAX_ARTICLES_TO_ANALYZE + 
                           " articles with estimated word count > " + MIN_PROXIED_WORD_COUNT + "...");
        
        try {
            java.net.URI uri = java.net.URI.create(NEWS_API_URL);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-Api-Key", API_KEY); 
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
            } else {

                System.err.println("NewsAPI Error - HTTP Code: " + responseCode);
                if (conn.getErrorStream() != null) {
                    try (BufferedReader err = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                        String errorLine;
                        while ((errorLine = err.readLine()) != null) {
                            System.err.println(errorLine);
                        }
                    }
                } else {
                    System.err.println("No error stream available from connection.");
                }
                return allArticles; 
            }

            String json = response.toString();
            
            Pattern articlePattern = Pattern.compile(
                "\"source\":\\{\"id\":.*?,\"name\":\"(.*?)\"\\}.*?\"title\":\"(.*?)\".*?\"description\":\"(.*?)\".*?\"content\":\"(.*?)\"", 
                Pattern.DOTALL
            );
            Matcher matcher = articlePattern.matcher(json);
            
            int foundCount = 0;
            while (matcher.find() && foundCount < MAX_ARTICLES_TO_ANALYZE) {
                String sourceName = matcher.group(1).replace("\"", "").trim();
                String title = matcher.group(2).replace("\"", "").replace("\\", "").trim();
                String description = matcher.group(3).replace("\"", "").replace("\\", "").trim();
                String contentSnippet = matcher.group(4).replace("\"", "").replace("\\", "").trim();
                
                String combinedContent = title + ". " + description + ". " + contentSnippet; 
                
                // --- Filtering Logic (Proxy for 1000+ words) ---
                // Count words in the combined text.
                // A simple word count is based on splitting by whitespace.
                String[] words = combinedContent.split("\\s+");
                int combinedWordCount = words.length;

                if (combinedWordCount >= MIN_PROXIED_WORD_COUNT) {
                    allArticles.add(new Article(title, combinedContent, sourceName));
                    foundCount++;
                }
            }
            
        } catch (java.net.MalformedURLException e) {
            System.err.println("Malformed URL during API fetch: " + e.getMessage());
            System.err.println("Exception details: " + e);
        } catch (java.io.IOException e) {
            System.err.println("I/O error during API fetch: " + e.getMessage());
            System.err.println("Exception details: " + e);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid URI/URL provided: " + e.getMessage());
            System.err.println("Exception details: " + e);
        } catch (RuntimeException e) {
            System.err.println("Unexpected runtime error during API fetch: " + e.getMessage());
            System.err.println("Exception details: " + e);
        }
        
        return allArticles;
    }
    
    public static void main(String[] args) {

        // load
        String positiveLexiconContent = "; Comment line\nFANTASTIC\nSUCCESS\nTHrilled\nupGradE\ngreat\n";
        String negativeLexiconContent = "; Comment line\nTerrible\nbugs\nUnFoRtUnAtE\n";
        String scoredLexiconContent = 
            "fantastic\t3.0\nsuccess\t2.5\nterrible\t-3.5\nbugs\t-2.0\nunfortunate\t-1.5\ngreat\t3.0\nupgrade\t1.5\nthrilled\t2.0"; 

        HashSet<String> positiveWords = LexiconLoader.loadSimpleLexicon(positiveLexiconContent);
        HashSet<String> negativeWords = LexiconLoader.loadSimpleLexicon(negativeLexiconContent);
        HashMap<String, Double> scoredLexicon = LexiconLoader.loadScoredLexicon(scoredLexiconContent);
        
        System.out.println("Lexicon Load: Positive Words=" + positiveWords.size() + 
            ", Negative Words=" + negativeWords.size() + 
            ", Scored Lexicon=" + scoredLexicon.size());

        // Fetch
        ArrayList<Article> newsArticles = fetchNewsArticles();
        
        if (newsArticles.isEmpty()) {
             System.out.println("\nAnalysis stopped: Could not fetch articles from NewsAPI or no sufficiently long articles were found.");
             return;
        }
        
        System.out.println("\n=========================================================================");
        System.out.println("SUCCESS: Found and filtered " + newsArticles.size() + " articles for analysis.");
        System.out.println("=========================================================================");

        for (int i = 0; i < newsArticles.size(); i++) {
            Article currentArticle = newsArticles.get(i);
            String articleContent = currentArticle.content;
            String articleTitle = currentArticle.title;

            System.out.println("\n-------------------------------------------------------------------------");
            System.out.println("ARTICLE " + (i + 1) + ": " + articleTitle + " (Source: " + currentArticle.source + ")");
            System.out.println("-------------------------------------------------------------------------");

            ArrayList<String> allWords = FileLoader.loadAndPreprocessContent(articleContent);

            if (allWords.isEmpty()) {
                System.out.println("Analysis skipped: No words were processed for this article.");
                continue;
            }

            System.out.println("Initial Total Words (Pre-Stop-Word Removal): " + allWords.size());
            
            StopWords.filterWords(allWords);

            ArticleStatistics.generateStatsAndRank(allWords);
            
            SentimentAnalysis sentimentTool = new SentimentAnalysis(
                positiveWords, negativeWords, scoredLexicon
            );
            
            System.out.println("\n--- Sentiment Analysis (Milestone 2) ---");
            
            String simpleResult = sentimentTool.analyzeSimpleSentiment(allWords);
            System.out.println("Simple Sentiment: " + simpleResult);

            double averageScore = sentimentTool.analyzeScoredSentiment(allWords);
            System.out.println(String.format("Scored Lexicon Average: %.4f", averageScore));
        }

        System.out.println("\n=========================================================================");
        System.out.println("Analysis Complete for " + newsArticles.size() + " filtered articles.");
        System.out.println("=========================================================================");
    }
}