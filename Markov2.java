import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by goldage5 on 2/27/15.
 */
public class Markov2 {
    public static Hashtable<String, ArrayList> forwardMarkov = new Hashtable();
    public static Hashtable<String, ArrayList> backwardMarkov = new Hashtable();
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
        catch(Exception ex) {
            System.out.println("");
            ex.printStackTrace();
        }
        return forwardMarkov;
    }

    public Hashtable trainBackward( File file )
    {
        ArrayList<ArrayList<String>> endArrayList = new ArrayList<ArrayList<String>>();
        endArrayList.add(new ArrayList<String>());
        endArrayList.add(new ArrayList<String>());
        endArrayList.add(new ArrayList<String>());
        backwardMarkov.put("_end", endArrayList);
        backwardMarkov.put("_start", new ArrayList<ArrayList<String>>());

        String thisLine;

        try
        {
            BufferedReader br = new BufferedReader( new FileReader( file ) );
            while ( ( thisLine = br.readLine() ) != null ) {
                String[] wordList = thisLine.split( " " );
                for ( int i = 0; i < wordList.length; i++ ) {
                    //if starting words, no words before it
                    if ( i == 0 )
                    {
                        ArrayList<String> startWords = backwardMarkov.get( "_start" );
                        startWords.add( wordList[i] );
                    }
                    // if last word, add words before it
                    else if ( i == wordList.length - 1 )
                    {
                        // get the arraylist of end words
                        ArrayList<ArrayList<String>> endWords = backwardMarkov.get( "_end" );

                        //add the first end word
                        endWords.get( 0 ).add( wordList[i] );

                        if ( wordList.length > 1 ) {
                            // add the next word to the list of start words
                            endWords.get( 1 ).add( wordList[i - 1] );
                            if ( wordList.length > 2 ) {
                                // add the third word to the list of start words
                                endWords.get( 2 ).add( wordList[i - 2] );
                            }
                        }
                    }
                    //if any word in the middle
                    else
                    {
                        ArrayList<ArrayList<String>> followingWords = backwardMarkov.get( wordList[i] );

                        //get the current and three following words
                        String currentWord = wordList[i];
                        String firstPreviousWord = wordList[i - 1];
                        String secondPreviousWord = "";
                        String thirdPreviousWord = "";
                        if (i > 1)
                        {
                            secondPreviousWord = wordList[i - 2];
                            if (i > 2)
                            {
                                thirdPreviousWord = wordList[i - 3];
                            }
                        }

                        // if the current word hasn't been added yet
                        if (followingWords == null)
                        {
                            ArrayList<String> firstWords = new ArrayList<String>();
                            ArrayList<String> secondWords = new ArrayList<String>();
                            ArrayList<String> thirdWords = new ArrayList<String>();
                            firstWords.add(firstPreviousWord);
                            secondWords.add(secondPreviousWord);
                            thirdWords.add(thirdPreviousWord);
                            ArrayList<ArrayList<String>> wordsToAdd = new ArrayList<ArrayList<String>>();
                            wordsToAdd.add(firstWords);
                            wordsToAdd.add(secondWords);
                            wordsToAdd.add(thirdWords);
                            backwardMarkov.put(currentWord, wordsToAdd);
                        }
                        else
                        {
                            followingWords.get( 0 ).add(firstPreviousWord);
                            followingWords.get ( 1 ).add(secondPreviousWord);
                            followingWords.get( 2 ).add(thirdPreviousWord);
                        }
                    }
                }
            }
        }
        catch(Exception ex) {
            System.out.println("");
            ex.printStackTrace();
        }
        return backwardMarkov;
    }

    public String generateForward(String keyword)
    {
        /*  1) start with keyword
        *   2) choose random first following word
         *  3) choose random second following word
         *  4) choose random third following word
         *  5) repeat using third following word as keyword
         *  6) Stop when last character of any word has a period
        */
        String firstWord;
        String secondWord;
        String thirdWord;
        String output = keyword + " ";
        while (keyword.charAt(keyword.length() - 1) != '.')
        {
            firstWord = secondWord = thirdWord = "";
            ArrayList<ArrayList<String>> wordsList = forwardMarkov.get(keyword);
            if ( wordsList == null )
            {
                System.out.println("ERROR! WORD NOT FOUND!");
                break;
            }
            ArrayList<String> firstWords = wordsList.get( 0 );
            ArrayList<String> secondWords = wordsList.get( 1 );
            ArrayList<String> thirdWords = wordsList.get( 2 );
            firstWord = firstWords.get( new Random().nextInt(firstWords.size()) );
            if (firstWord.charAt(firstWord.length() - 1) == '.')
            {
                output += firstWord + " ";
                break;
            }
            secondWord = secondWords.get( new Random().nextInt(secondWords.size()) );
            if (secondWord.charAt(secondWord.length() - 1) == '.') {
                output += secondWord + " ";
                break;
            }
            thirdWord = thirdWords.get( new Random().nextInt(thirdWords.size()));
            if (thirdWord.charAt(thirdWord.length() - 1) == '.') {
                output += thirdWord + " ";
                break;
            }
            output += firstWord + " " + secondWord + " " + thirdWord + " ";
            keyword = thirdWord;
        }
        return output;
    }

    public String generateBackward(String keyword)
    {
        
    }

    public static void main(String args[])
    {
        Markov2 m = new Markov2();
        File trainingData = new File("prince.txt");
        /*Hashtable backward = m.trainBackward(trainingData);
        File file = new File("output.txt");
        try {
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(backward.toString());
            bw.close();
            System.out.println("File written");
        }
        catch(IOException ex){System.out.println("IOException");}*/
        m.trainForward(trainingData);
        System.out.println(m.generateForward("the"));
    }



}
