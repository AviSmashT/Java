import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/** This class will read words from a file. and add them to word array of type ArrayList **/
public class Database {
	private boolean DBStatus; // database status - active/inactive
	private File file; 
	private int wordsArraylength; // number of words read from file
	private ArrayList<String> wordsArray; // ArrayList of words read from file
	

	public Database() {
    	this.wordsArray = new ArrayList<String>();
    	this.file = new File("Words.txt");
    	this.DBStatus = createDB();
		updateArrayLength();
	}
	
	/** This method will add words from a file to ArrayList given as parameter**/
	private boolean createDB() {
		try {
			Scanner fileScanner = new Scanner(file);
			while(fileScanner.hasNextLine()) {
				wordsArray.add(fileScanner.nextLine().toLowerCase()); // add lines as lower case to match the game selection of ASCII letters available
			}
			fileScanner.close();
			return true;
		} catch (IOException e) {
			System.out.println("Error! File is inaccessible."); 
			return false;
		}
	}
	
	/** This method will return random word from the array of words. 
	 *  If all words had been used or no words was read from file, return null**/
	public String getRandWord() {
		if (wordsArraylength == 0) { // no words in DB
			return null;
		} // else:
		Random rand = new Random();
		int randNum = rand.nextInt(this.wordsArraylength); // get random number in array length range
		String randWord = this.wordsArray.get(randNum); // fetch random word using the random number
		this.wordsArray.remove(randNum); // remove the fetched word to avoid repeating the same word twice in the game
		updateArrayLength();
		return randWord;
	}
	
	/** This method will update the words ArrayList length **/
	private void updateArrayLength() {
		this.wordsArraylength = this.wordsArray.size();
	}
	
	
	public void rebuildDB() {
		this.DBStatus = createDB();
		updateArrayLength();
	}
	
	
	
	// Setters and Getters methods:
	public ArrayList<String> getWordsArray() {
		return wordsArray;
	}

	public void setWordsArray(ArrayList<String> wordsArray) {
		this.wordsArray = wordsArray;
	}

	public int getWordsArraylength() {
		return wordsArraylength;
	}

	public void setWordsArraylength(int wordsArraylength) {
		this.wordsArraylength = wordsArraylength;
	}

	public boolean getDBStatus() {
		return DBStatus;
	}
}
