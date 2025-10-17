import java.util.Iterator;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StopWords
{
    public static void removeStopWords(ArrayList<String> articleWords) throws Exception
    {
        ArrayList<String> stopWords = new ArrayList<>();
        Scanner s = null; // for file reading
        try
        {
            s = new Scanner(new BufferedReader(new FileReader("C:\\Users\\sionp\\Documents\\class\\25-26\\fall\\cpsc\\lab\\stopwords.txt")));

            // setting up stopword ArrayList
            while(s.hasNextLine())
            {
                stopWords.add(s.nextLine());
            }

            // actual removal
            Iterator<String> iterator = articleWords.iterator();
            while(iterator.hasNext())
            {
                String word = iterator.next().toLowerCase();
                if(stopWords.contains(word))
                {
                    iterator.remove();
                }
            }
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found. Please try again.");
        }
        finally
        {
            if(s != null)
            {
                s.close();
            }
        }
    }
}

