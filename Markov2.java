import java.io.*;
import java.util.*;


/**
 * Created by goldage5 on 2/27/15.
 */
public class Markov2 {
    public Hashtable<String, ArrayList> forwardMarkov = new Hashtable();
    public Hashtable<String, ArrayList> backwardMarkov = new Hashtable();
    public Random rnd = new Random();

    private Hashtable trainForward( File file )
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
                                startWords.get( 2 ).add( wordList[i + 2] );
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
                        String firstNextWord = wordList[i + 1];
                        String secondNextWord = "";
                        String thirdNextWord = "";
                        if (i < wordList.length - 2)
                        {
                            secondNextWord = wordList[i + 2];
                            if (i < wordList.length - 3)
                            {
                            thirdNextWord = wordList[i + 3];
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
        catch(Exception ignored) {}
        return forwardMarkov;
    }

    private Hashtable trainBackward( File file )
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
        catch(Exception ignored) {}
        return backwardMarkov;
    }

    private String generateBackward(String keyword) throws WordNotFoundException
    {
        String firstLastWord;
        String secondLastWord;
        String thirdLastWord;
        String output = " ";
        while (keyword.charAt(keyword.length() - 1) != '.')
        {
            firstLastWord = secondLastWord = thirdLastWord = "";
            ArrayList<ArrayList<String>> wordsList = backwardMarkov.get(keyword);
            if ( wordsList == null )
            {
                WordNotFoundException exception = new WordNotFoundException("Word not found.");
                throw exception;
            }
            ArrayList<String> firstLastWords = wordsList.get( 0 );
            ArrayList<String> secondLastWords = wordsList.get( 1 );
            ArrayList<String> thirdLastWords = wordsList.get( 2 );
            int location = rnd.nextInt(firstLastWords.size());
            firstLastWord = firstLastWords.get(location);
            if (firstLastWord.charAt(firstLastWord.length() - 1) == '.') {
                break;
            }
            secondLastWord = secondLastWords.get(location);
            if (secondLastWord.charAt(secondLastWord.length() - 1) == '.') {
                output = firstLastWord + " "  + output;
                break;
            }
            thirdLastWord = thirdLastWords.get(location);
            if (thirdLastWord.charAt(thirdLastWord.length() - 1) == '.') {
                output = firstLastWord + " " + secondLastWord + " " + output;
                break;
            }
            output = thirdLastWord + " " + secondLastWord + " " + firstLastWord + " " + output;
            keyword = thirdLastWord;
        }

        //fix the issue with first and second words being switched sometimes
        ArrayList<String> outputWords = new ArrayList<String>(Arrays.asList(output.split(" ")));
        if (outputWords.size() > 1)
        {
            String first = Character.toString(outputWords.get(0).charAt(0));
            String second = Character.toString(outputWords.get(1).charAt(0));
            if (first.equals(first.toLowerCase()) && second.equals(second.toUpperCase()))
            {
                Collections.swap(outputWords, 0, 1);
            }
        }
        output = "";
        for (String outputWord : outputWords) {
            output += outputWord + " ";
        }

        return output;
    }

    private String generateForward(String keyword) throws WordNotFoundException
    {
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
                WordNotFoundException exception = new WordNotFoundException("Word not found.");
                throw exception;
            }
            ArrayList<String> firstWords = wordsList.get( 0 );
            ArrayList<String> secondWords = wordsList.get( 1 );
            ArrayList<String> thirdWords = wordsList.get( 2 );
            int location = rnd.nextInt(firstWords.size());
            firstWord = firstWords.get(location);
            if (firstWord.charAt(firstWord.length() - 1) == '.')
            {
                output += firstWord + " ";
                break;
            }
            secondWord = secondWords.get(location);
            if (secondWord.charAt(secondWord.length() - 1) == '.') {
                output += secondWord + " ";
                break;
            }
            thirdWord = thirdWords.get(location);
            if (thirdWord.charAt(thirdWord.length() - 1) == '.') {
                output += thirdWord + " ";
                break;
            }
            output += firstWord + " " + secondWord + " " + thirdWord + " ";
            keyword = thirdWord;
        }
        return output;
    }

    /*
     * Method to generate a sentence based off of a given keyword
     * Returns the sentence as a String if the generation was successful
     * Returns `void` if the keyword was not found
     */
    public String generateSentence(String keyword)
    {
        try {
            String first = generateBackward(keyword).trim();
            String second = generateForward(keyword).trim();
            return first + " " + second;
        }
        catch(WordNotFoundException ex)
        {
            return null;
        }
    }

    /*
     * Method to train the Markov
     * Takes a file with the training data
     */
    public void train(File file)
    {
        trainForward(file);
        trainBackward(file);
    }

    /*
     * Method to train the Markov
     * Takes a filename with the training data
     */
    public void train(String filename)
    {
        trainForward(new File(filename));
        trainBackward(new File(filename));
    }

    /*
     * Custom Exception to handle a word not being found when generating a sentence
     */
    class WordNotFoundException extends Exception {
        public WordNotFoundException(String message)
        {
            super(message);
        }

        public WordNotFoundException(String message, Throwable throwable)
        {
            super(message, throwable);
        }
    }
}
