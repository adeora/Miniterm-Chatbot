import java.io.*;
import java.util.ArrayList;

public class KeyFinder {
	
	private File nounFile;
	private File verbFile;
	private File conjFile;
	private File prepFile;
	private File adjFile;
	private File advFile;
	private ArrayList<String> nouns;
	private ArrayList<String> verbs;
	private ArrayList<String> conjs;
	private ArrayList<String> advs;
	private ArrayList<String> adjs;
	private ArrayList<String> preps;
	
	public KeyFinder() {
		nounFile = new File( "partsOfSpeechFiles/nouns.txt" );
		verbFile = new File( "partsOfSpeechFiles/verbs.txt" );
		conjFile = new File( "partsOfSpeechFiles/conjunctions.txt");
		prepFile = new File( "partsOfSpeechFiles/prepositions.txt");
		advFile = new File( "partsOfSpeechFiles/adverbs.txt");
		adjFile = new File( "partsOfSpeechFiles/adjectives.txt");
		nouns = new ArrayList<String>();
		verbs = new ArrayList<String>();
		conjs = new ArrayList<String>();
		advs = new ArrayList<String>();
		adjs = new ArrayList<String>();
		preps = new ArrayList<String>();
		uploadText();
	}
	
	private void uploadText() {
		String thisLine = "";
		try
        {
            BufferedReader br = new BufferedReader( new FileReader( nounFile ) );
			while( (thisLine = br.readLine()) != null ) {
				nouns.add( thisLine );
			}
			br = new BufferedReader( new FileReader( verbFile ) );
			while( (thisLine = br.readLine()) != null ) {
				verbs.add( thisLine );
			}
			br = new BufferedReader( new FileReader( conjFile ) );
			while( (thisLine = br.readLine()) != null ) {
				conjs.add( thisLine );
			}
			br = new BufferedReader( new FileReader( advFile ) );
			while( (thisLine = br.readLine()) != null ) {
				advs.add( thisLine );
			}
			br = new BufferedReader( new FileReader( adjFile ) );
			while( (thisLine = br.readLine()) != null ) {
				adjs.add( thisLine );
			}
			br = new BufferedReader( new FileReader( prepFile ) );
			while( (thisLine = br.readLine()) != null ) {
				preps.add( thisLine );
			}
			br.close();
		}
		catch( IOException ex ) {
			ex.printStackTrace();
		}
	}
	
	public ArrayList<String> findKeys2( String sentence ) {
		//System.out.println( "Finding keys to sentence: [" + sentence + "]" );
		String[] words = sentence.split(" ");
		
		//find the subject, main verb and the object if any
		ArrayList<String> nv = new ArrayList<String>();
		for( int i = 0; i < words.length; i++ ) {
			String wordCopy = words[i];
			wordCopy = wordCopy.replaceAll( "[^A-Za-z]", "" );
			boolean noun = isNoun( wordCopy, i == 0 );
			boolean verb = isVerb( wordCopy );
			if( noun && verb ) {
				nv.add( wordCopy );
			}
			else {
				if( noun || verb ) {
					nv.add( wordCopy );
				}
				else {
					continue;
				}
			}
		}
		return nv;
	}
	
	private boolean isNoun( String test, boolean isFirstWord ) {
		for( String noun: nouns ) {
			if( test.toLowerCase().equals(noun) ) {
				return true;
			}
		}
		if( Character.isUpperCase( test.charAt(0) ) && !isFirstWord ) {
			return true;
		}
		return false;
	}
	
	private boolean isPrep( String test ) {
		for( String prep: preps ) {
			if( test.toLowerCase().equals(prep) ) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isVerb( String test ) {
		for( String verb: verbs ) {
			if( test.toLowerCase().equals(verb) ) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isConj( String test ) {
		for( String conj: conjs ) {
			if( test.toLowerCase().equals(conj) ) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isAdv( String test ) {
		for( String adv: advs ) {
			if( test.toLowerCase().equals(adv) ) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isAdj( String test ) {
		for( String adj: adjs ) {
			if( test.toLowerCase().equals(adj) ) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<String> findKeys( String sentence ) {
		//System.out.println( "Finding keys to sentence: [" + sentence + "]" );
		String[] words = sentence.split(" ");
		ArrayList<String> nounList = new ArrayList<String>();
		for( int i = 0; i < words.length; i++ ) {
			String wordCopy = words[i];
			wordCopy = wordCopy.replaceAll( "[^A-Za-z]", "" );
			for( String noun: nouns ) {
				if( wordCopy.equals(noun) ) {
					String word = words[i].replaceAll("[^A-Za-z']", "").toLowerCase();
					nounList.add( word );
					nounList.add(Character.toUpperCase(word.charAt(0)) + word.substring(1));
				}
			}
		}
		return nounList;
	}
	
	public static void main( String[] args ) {
		KeyFinder kf = new KeyFinder();
		ArrayList<String> keys = kf.findKeys2( "That boy ate my sandwich; he is so mean." );
		System.out.println( keys );
	}
}
