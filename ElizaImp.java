import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.HashMap;

public class ElizaImp {

    public static Scanner input;
    public static boolean isDone;
    public static String[] exitStr = { "bye", "goodbye", "i'm leaving", "we're done here"};
    public static String[] keyWords = { "always", "because", "sorry", "maybe", "i think",
                                        "you", "yes", "no", "i am", "i'm", "i feel", "family",
                                        "mother", "mom", "dad", "father", "sister", "brother",
                                        "husband", "wife", "dream", "nightmare" };
    public static HashMap<String, String[]> responseList;
    
    public static void main( String[] args ) {
        //INITIALIZATION
        input = new Scanner( System.in );
        isDone = false;
        responseList = returnInitList();
        System.out.println("Hello. What can I do for you today?");

        //MAIN LOOP
        while( !isDone ) {
            System.out.print( ">> " );
            String raw = input.nextLine().toLowerCase();
            //check for exit string, indicated in exitStr list
            for( String exit: exitStr ) {
                if( raw.contains(exit) ) {
                    isDone = true;
                    break;
                }
            }
            if( isDone ) {
                break;
            }
            //otherwise, return a response
            String response = response( raw );
            System.out.println( response );
        }
    }

    public static HashMap<String, String[]> returnInitList() {
        HashMap<String, String[]> list = new HashMap<String, String[]>();

        //this series of responses can be altered to change the behavior of the chatbot
        String[] temp0 = { "What does that suggest to you?",
                           "I see.",
                           "I'm not sure I understand you fully.",
                           "Can you elaborate?",
                           "Interesting." };
        list.put("DEFAULT", temp0);

        String[] temp1 = { "Can you think of a specific example?" };
        list.put("always", temp1);

        String[] temp2 = { "Is that the real reason?" };
        list.put("because", temp2);

        String[] temp3 = { "Please don't apologize." };
        list.put("sorry", temp3);

        String[] temp4 = { "You don't seem very certain." };
        list.put("maybe", temp4);

        String[] temp5 = { "Do you really think so?" };
        list.put("i think", temp5);

        String[] temp6 = { "We are discussing you, not me." };
        list.put("you", temp6);

        String[] temp7 = { "Why do you think so?",
                           "You seem quite certain." };
        list.put("yes", temp7);

        String[] temp8 = { "Why not?",
                            "Are you sure?" };
        list.put("no", temp8);

        String[] temp9 = { "I am sorry to hear that you are *.",
                           "How long have you been *?",
                           "Do you believe it's normal to be *?",
                           "Do you enjoy being *?" };
        list.put("i am", temp9);
        list.put("i'm", temp9);

        String[] temp10 = { "Do you often feel *?",
                            "Tell me more about these feelings.",
                            "Do you enjoy feeling *?",
                            "Why do you feel that way?" };
        list.put("i feel", temp10);

        String[] temp11 = { "Tell me more about your family.",
                            "How do you get along with your family?",
                            "Is your family important to you?" };
        list.put("family", temp11);
        list.put("mother", temp11);
        list.put("father", temp11);
        list.put("mom", temp11);
        list.put("dad", temp11);
        list.put("sister", temp11);
        list.put("brother", temp11);
        list.put("husband", temp11);
        list.put("wife", temp11);

        String[] temp12 = { "What does that dream suggest to you?",
                            "Do you dream often?",
                            "What persons appear in your dreams?",
                            "Are you disturbed by your dreams?" };
        list.put("dream", temp12);
        list.put("nightmare", temp12);

        return list;
    }

    public static String response( String input ) {
        //loop through the keywords, and find a match in the input
        boolean foundKey = false;
        String response = "";
        String[] responseArray;
        String[] inputList = input.split(" ");

        //main loop
        for( String key: keyWords ) {
            //found match, fetch a random response from the list, then check for *
            if( input.contains(key) ) {
                int start = input.indexOf(key);
                int end = start + key.length();
                responseArray = responseList.get(input.substring(start, end));
                //response = responseArray[ (int)((responseArray.length - 1) * Math.random()) ];
                response = responseArray[0];

                //if * exists, then pull the remaining string and insert into response
                if( response.contains("*") ) {
                    //look for remaining string after the end index

                    String insertStr = input.substring(end + 1, input.length()).trim();

                    // input = the user's input
                    // start = start location of key
                    // end = end location of key
                    // end to input.length() - 1

                    if ( insertStr.contains(";") )
                    {
                        insertStr = insertStr.substring(0, insertStr.indexOf(";"));
                    }

                    //insert into the response

                    response = response.replace("*", insertStr);
                }

                //indicate that key was found
                foundKey = true;
                break;
            }
        }

        //by default, if none of the keywords are found, then answer using the DEFAULT key
        if( !foundKey ) {
            responseArray = responseList.get("DEFAULT");
            response = responseArray[ (int)((responseArray.length - 1) * Math.random()) ];
        }
        return response;
    }
}
