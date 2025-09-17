public class TextAnalysis // main class
{
    private String article1;
    private String article2;
    private String article3;

    public TextAnalysis(String article1, String article2, String article3)
    {
        this.article1 = article1;
        this.article2 = article2;
        this.article3 = article3;
    }

    public static void main(String[] args) // main method
    {
        TextAnalysis a1 = new TextAnalysis("article 1", "article 2", "article 3");
        // think about how to import article text files into JRE

        while(true) // menu loop
        {
            // TODO : recall accepting user input without creating a new obj every time

        }

    }

    public static boolean menu(int option)
    {
        // TODO : case switch; recall java case switch syntax
        return true;
    }

}
