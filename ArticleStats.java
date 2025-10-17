import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class ArticleStats
{
    public static void main(String[] args) throws Exception
    {
        // initializations
        ArrayList<String> uniqueWords = new ArrayList<>();
        ArrayList<Integer> wordCounts = new ArrayList<>();
        String articleText = "";

        Scanner s = null; // for file reading
        try
        {
            // replace("\"", "") removes quotes, so this can work using the copied file path if through Windows
            // reads file, concatenates each line into a huge string
            FileLoader directory = new FileLoader();
            for (String file : directory.getTopic1()) {
                File myObj = new File(file.replace("\"", ""));
                Scanner myReader = new Scanner(myObj);

                while (myReader.hasNextLine()) {
                    articleText += myReader.nextLine() + " "; // space required to not accidentally concatenate words together!!
                }

                String[] wordArray = (articleText.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+"));
                ArrayList<String> wordList = new ArrayList<>(Arrays.asList(wordArray));
                System.out.println("Total number of words in the article (incl. stop words): " + wordList.size());
                StopWords.removeStopWords(wordList);
                System.out.println("Total number of words in the article (excl. stop words): " + wordList.size());

                for (String word : wordList) {
                    int index = uniqueWords.indexOf(word);
                    if (index != -1) {
                        wordCounts.set(index, wordCounts.get(index) + 1);
                    } else {
                        uniqueWords.add(word);
                        wordCounts.add(1);
                    }
                }
                System.out.println("Number of unique words in the article (excl. stop words): " + uniqueWords.size());
                sortByRank(uniqueWords, wordCounts);
                System.out.println("Top ten words ranked by frequency: ");
                //for(int i = 0; i < uniqueWords.size(); i++) // prints ranking of ALL WORDS; for a top XX modify middle argument
                for (int i = 0; i < 10; i++) // prints ranking of TOP 10 WORDS
                {
                    System.out.println(uniqueWords.get(i) + ": " + wordCounts.get(i));
                }
                System.out.println("(End of output for this article.)\n");
            }

        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found. Please try again.");
        }
        catch(IOException e)
        {
            System.out.println("Wrong file path. Please try again.");
        }
        finally
        {
            if(s != null)
            {
                System.out.println("End of output.");
                s.close();
            }
        }
    }

    public static void sortByRank(ArrayList<String> words, ArrayList<Integer> wordCounts)
    {
        for(int i = 0; i < words.size() - 1; i++)
        {
            for(int j = 0; j < wordCounts.size() - 1; j++)
            {
                if(wordCounts.get(j) < wordCounts.get(j + 1))
                {
                    int tempCount = wordCounts.get(j);
                    wordCounts.set(j, wordCounts.get(j + 1));
                    wordCounts.set(j + 1, tempCount);

                    String tempWord = words.get(j);
                    words.set(j, words.get(j + 1));
                    words.set(j + 1, tempWord);
                }
            }
        }
    }
}
