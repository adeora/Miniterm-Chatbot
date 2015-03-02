import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by goldage5 on 2/27/15.
 */
public class Markov2 {
    public static Hashtable<String, ArrayList> forwardMarkov = new Hashtable();
    Random rnd = new Random();

    public Hashtable trainForward( File file )
    {
        ArrayList<ArrayList<String>> startArrayList = new ArrayList<ArrayList<String>>();
        startArrayList.add(new ArrayList<String>());
        startArrayList.add(new ArrayList<String>());
        startArrayList.add(new ArrayList<String>());
        forwardMarkov.put("_start", startArrayList);
        forwardMarkov.put("_end", new ArrayList<ArrayList<String>>());

        String thisLine;

        try
        {
            BufferedReader br = new BufferedReader( new FileReader( file ) );
            while ( ( thisLine = br.readLine() ) != null ) {
                String[] wordList = thisLine.split( " " );
                for ( int i = 0; i < wordList.length; i++ ) {
                    //if first word
                    if ( i == 0 )
                    {
                        // get the arraylist of start words
                        ArrayList<ArrayList<String>> startWords = forwardMarkov.get( "_start" );

                        //add the first start word
                        ArrayList<String> firstStartWords = startWords.get( 0 );
                        firstStartWords.add( wordList[i] );

                        if ( wordList.length > 1 ) {
                            // add the next word to the list of start words
                            startWords.get( 1 ).add(wordList[i + 1]);
                            if ( wordList.length > 2 ) {
                                // add the third word to the list of start words
                                startWords.get( 2 ).add( wordList[i+2] );
                            }
                        }
                    }
                    // if last word
                    else if ( i == wordList.length - 1 )
                    {
                        ArrayList<String> endWords = forwardMarkov.get( "_end" );
                        endWords.add( wordList[i] );
                    }
                    //if any word in the middle
                    else
                    {
                        ArrayList<ArrayList<String>> followingWords = forwardMarkov.get( wordList[i] );

                        //get the current and three following words
                        String currentWord = wordList[i];
                        String firstNextWord = wordList[i+1];
                        String secondNextWord = "";
                        String thirdNextWord = "";
                        if (i < wordList.length - 2)
                        {
                            secondNextWord = wordList[i+2];
                            if (i < wordList.length -3)
                            {
                            thirdNextWord = wordList[i+3];
                            }
                        }

                        // if the current word hasn't been added yet
                        if (followingWords == null)
                        {
                            ArrayList<String> firstWords = new ArrayList<String>();
                            ArrayList<String> secondWords = new ArrayList<String>();
                            ArrayList<String> thirdWords = new ArrayList<String>();
                            firstWords.add(firstNextWord);
                            secondWords.add(secondNextWord);
                            thirdWords.add(thirdNextWord);
                            ArrayList<ArrayList<String>> wordsToAdd = new ArrayList<ArrayList<String>>();
                            wordsToAdd.add(firstWords);
                            wordsToAdd.add(secondWords);
                            wordsToAdd.add(thirdWords);
                            forwardMarkov.put(currentWord, wordsToAdd);
                        }
                        else
                        {
                            followingWords.get( 0 ).add(firstNextWord);
                            followingWords.get ( 1 ).add(secondNextWord);
                            followingWords.get( 2 ).add(thirdNextWord);
                        }
                    }
                }
            }
        }
        catch(Exception ex) {ex.printStackTrace();}
        return forwardMarkov;
    }

    public static void main(String args[])
    {
        Markov2 m = new Markov2();
        Hashtable forward = m.trainForward(new File("prince.txt"));
        System.out.println(forward);
        m.generateForward("Israel");
        System.out.println("");
        m.generateForward("Machiavelli");
        System.out.println("");
        m.generateForward("A");
        System.out.println("");
    }

    public void generateForward(String keyword)
    {
        /*  1) start with keyword
        *   2) choose random first following word
         *  3) choose random second following word
         *  4) choose random third following word
         *  5) repeat using third following word as keyword
         *  6) Stop when last character of any word has a period
        */
        System.out.print(keyword + " ");
        String firstWord;
        String secondWord;
        String thirdWord;
        while ( keyword.charAt(keyword.length() - 1) != '.' )
        {
            firstWord = secondWord = thirdWord = "";
            ArrayList<ArrayList<String>> wordsList = forwardMarkov.get(keyword);
            ArrayList<String> firstWords = wordsList.get( 0 );
            ArrayList<String> secondWords = wordsList.get( 1 );
            ArrayList<String> thirdWords = wordsList.get( 2 );
            firstWord = firstWords.get( new Random().nextInt(firstWords.size()) );
            if (firstWord.charAt(firstWord.length() - 1) == '.')
            {
                System.out.print(firstWord);
                break;
            }
            secondWord = secondWords.get( new Random().nextInt(secondWords.size()) );
            if (secondWord.charAt(secondWord.length() - 1) == '.')
            {
                System.out.print(secondWord);
                break;
            }
            thirdWord = thirdWords.get( new Random().nextInt(thirdWords.size()));
            if (secondWord.charAt(secondWord.length() - 1) == '.')
            {
                System.out.print(thirdWord);
                break;
            }
            System.out.print(firstWord + " " + secondWord + " " + thirdWord + " ");
            keyword = thirdWord;
        }

    }

}
