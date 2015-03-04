import java.io.File;
import java.util.ArrayList;

/**
 * Created by goldage5 on 3/3/15.
 */
public class MarkovTester {
    ArrayList<String> wordList;

    public static void main(String args[])
    {
        Markov3 m = new Markov3();
        m.train(new File("corpora/conversation-data-single-line.txt"));

        ArrayList<String> wordList = new ArrayList<String>();
        wordList.add("Hello");

        for (String s : wordList) {
            System.out.println(m.generateSentence(s));
        }

    }
}
