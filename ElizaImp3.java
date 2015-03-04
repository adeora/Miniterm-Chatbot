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
import java.util.ArrayList;
import java.io.*;

public class ElizaImp3 {

    public static Scanner input;
    public static boolean isDone;
    public static HashMap<String, String> changeList;
	public static Markov3 m3;
	public static File file;

    public static final String[] EXITSTR = {"goodbye", "bye", "adios"};
    
    public static void main( String[] args ) {
        //INITIALIZATION
        input = new Scanner( System.in );
        isDone = false;
        changeList = initChangeList();
		m3 = new Markov3();
		file = new File("conversation-data-single-line.txt");
		m3.train(file);
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
        String[] words = test.toLowerCase().split(" ");
		
		//find and remove punctuation
		for( int i = 0; i < words.length; i++ ) {
			if( words[i].lastIndexOf("?") == words[i].length() - 1 || words[i].lastIndexOf("!") == words[i].length() - 1 || words[i].lastIndexOf(".") == words[i].length() - 1  ) {
				words[i] = words[i].substring(0, words[i].length() - 1);
			}
		}

        //loop through to find if the word matches with changeList
        for( int i = 0; i < words.length; i++ ) {
            if( changeList.get(words[i]) != null ) {
                words[i] = changeList.get(words[i]);
            }
        }

        //reform the list into the appropriate string
        String ret = "";
        for( int i = 0; i < words.length; i++ ) {
			if( words[i].equals("the") || words[i].equals("a") || words[i].equals("an") ) {
				continue;
			}
            ret += (words[i] + " ");
        }
        ret = ret.trim();
        return ret;
    }

    public static HashMap<String, String> initChangeList() {
        HashMap<String, String> list = new HashMap<String, String>();

        //decline all forms of "you", and associate that with the corresponding forms of "I"
        list.put( "you", "i" );
        list.put( "your", "my" );
		list.put( "you'd", "i'd" );
		list.put( "you'll", "i'll" );
		list.put( "you're", "i'm" );
		list.put( "you've", "i've" );
		list.put( "yourself", "myself" );
		list.put( "yours", "mine" );

        //and vice versa: "I" to "you"
        list.put( "i", "you" );
        list.put( "me", "you" );
        list.put( "my", "your" );
		list.put( "mine", "yours" );
		list.put( "myself", "yourself" );
		list.put( "i'd", "you'd" );
		list.put( "i'll", "you'll" );
		list.put( "i've", "you've" );
		list.put( "i'm", "you're" );
        
        //associate "why" with "because"
        list.put( "why", "because" );
		list.put( "because", "why" );
		
		//associate no with yes
		list.put( "no", "yes" );
		list.put( "yes", "no" );
		
		//others
		list.put( "like", "dislike" );
		list.put( "dislike", "like" );
		list.put( "love", "hate" );
		list.put( "hate", "love" );
		
        return list;
    }

    public static String response( String input ) {
        //use the Markov model to process the input to provide the best response
		//first, identify the keywords needed to input into the Markov model
		KeyFinder kf = new KeyFinder();
		ArrayList<String> keys = kf.findKeys(input);
		System.out.println( "KEYS: " + keys );
		String[][] responses = new String[keys.size()][10];
		for( int j = 0; j < keys.size(); j++ ) {
			for( int i = 0; i < 10; i++ ) {
				String newSentence = m3.generateSentence(keys.get(j));
				if( newSentence != null ) {
					responses[j][i] = newSentence;
				}
				else {
					responses[j][i] = "";
				}
			}
		}
		//test the array for the sentences.
		/*for( int j = 0; j < keys.size(); j++ ) {
			for( int i = 0; i < 10; i++ ) {
				System.out.println(responses[j][i]);
			}
		}*/
		
		//test the sentences for keys, the one with the most keys will be the chosen sentence
		int maxKeyMatches = 0;
		String bestSentence = "";
		for( int j = 0; j < keys.size(); j++ ) {
			for( int i = 0; i < 10; i++ ) {
				int tempKeyMatches = 0;
				for( String key: keys ) {
					if( responses[j][i].contains(key) ) {
						tempKeyMatches++;
					}
				}
				if( tempKeyMatches > maxKeyMatches ) {
					maxKeyMatches = tempKeyMatches;
					bestSentence = responses[j][i];
				}
			}
		}
		return bestSentence.equals("") ? "..." : bestSentence;
    }
}
