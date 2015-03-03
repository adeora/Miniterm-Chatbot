import java.io.*;
import java.util.ArrayList;

public class KeyFinder {
	
	private File nounFile;
	private ArrayList<String> nouns;
	
	public KeyFinder() {
		nounFile = new File( "partsOfSpeechFiles/nouns.txt" );
		nouns = new ArrayList<String>();
		uploadText();
	}
	
	private void uploadText() {
		String thisLine = "";
		try
        {
            BufferedReader br = new BufferedReader( new FileReader( nounFile ) );
			while( (thisLine = br.readLine()) != null ) {
				System.out.println( thisLine );
				nouns.add( thisLine );
			}
		}
		catch( IOException ex ) {
			ex.printStackTrace();
		}
	}
	
	public ArrayList<String> findKeys( String sentence ) {
		System.out.println( "Finding keys to sentence: [" + sentence + "]" );
		String[] words = sentence.split(" ");
		ArrayList<String> nounList = new ArrayList<String>();
		for( int i = 0; i < words.length; i++ ) {
			String wordCopy = words[i].toLowerCase();
			wordCopy = wordCopy.replaceAll( "[^A-Za-z]", "" );
			for( String noun: nouns ) {
				if( wordCopy.equals(noun) ) {
					nounList.add( words[i].replaceAll("[^A-Za-z]", "") );
				}
			}
		}
		return nounList;
	}
	
	public static void main( String[] args ) {
		KeyFinder kf = new KeyFinder();
		ArrayList<String> keys = kf.findKeys( "Today is the day!" );
		System.out.println( keys );
	}
}
