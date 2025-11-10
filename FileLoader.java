// FileLoader.java
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {

    public static ArrayList<String> loadAndPreprocessContent(String articleContent) {
        ArrayList<String> processedWords = new ArrayList<>();
        
        try (Scanner contentScanner = new Scanner(articleContent)) {

            while (contentScanner.hasNext()) {
                String word = contentScanner.next();
                

                word = word.toLowerCase(); 
                
                word = word.replaceAll("[^a-z0-9]", ""); 

                if (!word.isEmpty()) { 
                    processedWords.add(word);
                }
            }
        }
        
        return processedWords;
    }
}