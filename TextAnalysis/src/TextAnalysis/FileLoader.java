import java.util.ArrayList;

public class FileLoader
{
    // helps load articles in one topic
    // saves path of each file as constant strings
    // uses iteration to load them one by one
    // ensures object interaction between classes

    public static final String FILE_ONE = "\"C:\\Users\\sionp\\Documents\\class\\25-26\\fall\\cpsc\\lab\\topics\\cpu\\topic_1-1.txt\"";
    public static final String FILE_TWO = "\"C:\\Users\\sionp\\Documents\\class\\25-26\\fall\\cpsc\\lab\\topics\\cpu\\topic_1-2.txt\"";
    public static final String FILE_THREE = "\"C:\\Users\\sionp\\Documents\\class\\25-26\\fall\\cpsc\\lab\\topics\\cpu\\topic_1-3.txt\"";
    public static final String FILE_FOUR = "\"C:\\Users\\sionp\\Documents\\class\\25-26\\fall\\cpsc\\lab\\topics\\politic\\topic_2-1.txt\"";

    private ArrayList<String> topic1;
    private ArrayList<String> topic2;
    private ArrayList<String> topic3;

    public FileLoader()
    {
        topic1 = new ArrayList<String>();
        topic1.add(FILE_ONE);
        topic1.add(FILE_TWO);
        topic1.add(FILE_THREE);

        topic2 = new ArrayList<String>();
        topic2.add(FILE_FOUR);

        topic3 = new ArrayList<String>();

    }

    public ArrayList<String> getTopic1()
    {

        return topic1;
    }

}
