/**
 * REVISION 3: ElizaImp
 * The primary goal of this revision of Eliza is to completely remove the psychotherapeutic element in Eliza's
 * programming
 * We intend to make Eliza's code solely Markov Model based, using a few preprocessing steps to make
 * the conversation more personal to the user
 *
 * CHANGES:
 * -    change any form of "you" to "I"
 * -    change any form of "why" to "because"
 */

import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;

public class ElizaImp3 {

    public static Scanner input;
    public static boolean isDone;
    public static HashMap<String, String> changeList;

    public static final String[] EXITSTR = {"goodbye", "bye", "adios"};
    
    public static void main( String[] args ) {
        //INITIALIZATION
        input = new Scanner( System.in );
        isDone = false;
        changeList = initChangeList();
        System.out.println("Hello. What can I do for you today?");

        //MAIN LOOP
        while( !isDone ) {
            System.out.print( ">> " );
            String raw = input.nextLine().toLowerCase();
            //check for exit string, indicated in exitStr list
            for( String exit: EXITSTR ) {
                if( raw.contains(exit) ) {
                    isDone = true;
                    break;
                }
            }
            if( isDone ) {
                break;
            }
            //otherwise, begin processing
            //first preprocess the string for words that need to be changed via changeList
            raw = preprocess( raw ); 
            System.out.println( "Preprocess: " + raw );

            //then process the response
            String response = response( raw );
            System.out.println( response );
        }
    }

    public static String preprocess( String test ) {
        //obtain a list of words
        String[] words = test.split(" ");

        //loop through to find if the word matches with changeList
        for( int i = 0; i < words.length; i++ ) {
            if( changeList.get(words[i]) != null ) {
                words[i] = changeList.get(words[i]);
            }
        }

        //reform the list into the appropriate string
        String ret = "";
        for( String word: words ) {
            ret += (word + " ");
        }
        ret = ret.trim();
        if( !ret.contains("!") && !ret.contains("?") ) {
            if( ret.lastIndexOf(".") != ret.length() - 1 ) {
                ret += ".";
            }
        }
        return ret;
    }

    public static HashMap<String, String> initChangeList() {
        HashMap<String, String> list = new HashMap<String, String>();

        //decline all forms of "you", and associate that with the corresponding forms of "I"
        list.put( "you", "i" );
        list.put( "your", "my" );

        //and vice versa: "I" to "you"
        list.put( "i", "you" );
        list.put( "me", "you" );
        list.put( "my", "your" );
        
        //associate "why" with "because"
        list.put( "why", "because" );

        return list;
    }

    public static String response( String input ) {
        //use the Markov model to process the input to provide the best response
        String response = "";
        return response;
    }
}
