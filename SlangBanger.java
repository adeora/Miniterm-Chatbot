/**
 * The purpose of this program is to build association to slang words 
 * and misspellings
 */

import java.util.HashMap;
import java.util.Scanner;

public class SlangBanger {

    public static HashMap<String, String> assoc;
    public static Scanner input;
    public static boolean isDone;

    public static void main( String[] args ) {
        System.out.println( "Initializing..." ); 

        //create the hashmap for associating words with slang and misspellings
        assoc = new HashMap<String, String>();
        //create the scanner
        input = new Scanner( System.in );
        //isDone
        isDone = false;
        //initialization
        System.out.println( "Please type a word (slang, misspelling, normal)" );
        initAssoc();

        //MAIN LOOP
        while( !isDone ) {
            System.out.print( ">> " );
            String raw = input.nextLine().toLowerCase();
            if( raw.contains( "bye" ) ) {
                isDone = true;
            }
            if( isDone ) {
                break;
            }
            //otherwise, process input
            process( raw );
        }
    }

    public static void process( String str ) {
        //first check if the words exists in the assoc 
        if( assoc.containsKey(str) ) {
            //the string is a correct phrase
            System.out.println( "I know this word: " + str );
            System.out.println( "It is associated with the slang/misspellings: " + assoc.get(str) );

        }
        else if( assoc.containsValue(str) ) {
            //the string is slang or misspelling
            System.out.println( "I know this misspelling/slang: " + str );
            //TODO: find the key associated with this particular value
        }
        else {
            //the string does not exist in assoc
            System.out.println( "I am not familiar with this term '" + str + "'" );
            System.out.println( "Is this word slang or a misspelling (y/n)?" );
            System.out.print( ">> ");
            String response = input.nextLine().toLowerCase();
            if( response.equals("y") ) {
                System.out.println( "Please type the word associated with this slang/misspelling." );
                System.out.print( ">> " );
                String word = input.nextLine().toLowerCase();
                //upload to the assoc table
                assoc.put( word, str );
                return;
            }
            else if( response.equals("n") ) {
                System.out.println( "Please type a slang/misspelling associated with the word." );
                System.out.print( ">> " );
                String slang = input.nextLine().toLowerCase();
                assoc.put( str, slang );
                return;
            }
            else {
                System.out.println( "Incorrect response." );
                return;
            }
        }

    }

    public static void initAssoc() {
        assoc.put( "google", "gooogle" ); 
        assoc.put( "google", "gooooooogle" );
    }
}
