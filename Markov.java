import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by goldage5 on 2/26/15.
 */

public class Markov {
    public static Hashtable<String, ArrayList<String>> markovChain = new Hashtable<String, ArrayList<String>>();

    public Hashtable<String, ArrayList<String>> generate(File file)
    {
        markovChain.put("_start", new ArrayList<String>());
        markovChain.put("_end", new ArrayList<String>());
        String thisLine;
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((thisLine = br.readLine()) != null) {
                if (thisLine != "") {
                    String[] wordList = thisLine.split(" ");
                    for (int i = 0; i < wordList.length; i++) {
                        //if first word
                        if (i == 0) {
                            if (wordList[i] != "\n")
                            {
                                //add the word to the values for '_start'
                                ArrayList<String> startWords = markovChain.get("_start");
                                startWords.add(wordList[i]);
                            }
                            if (wordList.length != 1) {
                                // add the following word to the key for the current word
                                // if key for current word doesn't exist, create it
                                ArrayList<String> suffix = markovChain.get(wordList[i]);
                                if (suffix == null) {
                                    if (wordList[i +1] != "\n") {
                                        suffix = new ArrayList<String>();
                                        suffix.add(wordList[i + 1]);
                                        markovChain.put(wordList[i], suffix);
                                    }
                                }
                            }
                        }
                        // if last word
                        else if (i == wordList.length - 1) {
                            // add the current word to the list of ending words
                            ArrayList<String> endWords = markovChain.get("_end");
                            if (wordList[i] != "\n") { endWords.add(wordList[i]); }
                        }
                        // otherwise
                        else {
                            // get the list of following words
                            ArrayList<String> suffix = markovChain.get(wordList[i]);
                            // if none exist, add them
                            if (suffix == null) {
                                if (wordList[i + 1] != "\n") {
                                    suffix = new ArrayList<String>();
                                    suffix.add(wordList[i + 1]);
                                    markovChain.put(wordList[i], suffix);
                                }
                            }
                            // otherwise add the following word
                            else {
                                if (wordList[i + 1] != "\n") {
                                    suffix.add(wordList[i + 1]);
                                    markovChain.put(wordList[i], suffix);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return markovChain;
    }

    public void generateSentence()
    {
        Random rnd = new Random();
        ArrayList<String> newPhrase = new ArrayList<String>();
        String nextWord = "";
        ArrayList<String> startWords = markovChain.get("_start");
        int startWordsLen = startWords.size();
        nextWord = startWords.get(rnd.nextInt(startWordsLen));
        newPhrase.add(nextWord);
        while (nextWord.charAt(nextWord.length()-1) != '.') {
            ArrayList<String> wordSelection = markovChain.get(nextWord);
            int wordSelectionLen = wordSelection.size();
            nextWord = wordSelection.get(rnd.nextInt(wordSelectionLen));
            newPhrase.add(nextWord);
        }
        String finalSentence = "";
        for (int i=0; i < newPhrase.size(); i++)
        {
            if (i == newPhrase.size())
            {
                finalSentence += newPhrase.get(i);
            }
            else {
                finalSentence += newPhrase.get(i) + " ";
            }
        }
        System.out.println(finalSentence);
    }

    public void generateSentence( String startWord )
    {
        Random rnd = new Random();
        ArrayList<String> newPhrase = new ArrayList<String>();
        String nextWord = "";
        ArrayList<String> startWords = markovChain.get(startWord);
        if ( startWords == null )
        {
            System.out.println("No sentence");
        }
        else
        {
            int startWordsLen = startWords.size();
            nextWord = startWords.get(rnd.nextInt(startWordsLen));
            newPhrase.add(nextWord);
            while (nextWord.charAt(nextWord.length()-1) != '.') {
                ArrayList<String> wordSelection = markovChain.get(nextWord);
                int wordSelectionLen = wordSelection.size();
                nextWord = wordSelection.get(rnd.nextInt(wordSelectionLen));
                newPhrase.add(nextWord);
            }
            String finalSentence = "";
            for (int i=0; i < newPhrase.size(); i++)
            {
                if (i == newPhrase.size())
                {
                    finalSentence += newPhrase.get(i);
                }
                else {
                    finalSentence += newPhrase.get(i) + " ";
                }
            }
            System.out.println(finalSentence);
        }
    }

    public static void main(String args[])
    {
        Markov m = new Markov();
        Hashtable values = m.generate(new File("prince.txt"));
        //System.out.println(values);
        m.generateSentence();
    }
}
