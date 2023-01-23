import java.util.ArrayList;

/** This Class represent Hangman game **/
public class StartGame{
	private Database db;
	private int hangingPhase; // hanging phase for current word (there are total 10 phases)
	private String currentWord; // current word 
	private int correctMatches; // number of correct matches by player on current word 
	final private int MAX_PHASE = 10;
	
	public StartGame() {
    	this.db = new Database();
    	this.currentWord = db.getRandWord();
    	this.hangingPhase = 0;
    	this.correctMatches = 0;
	}

	/** This method will check if the player guess is correct
	 *  If guess is correct, return true. else, return false.
	 *   If countMatches is true, the matched word will be counted **/
	public boolean checkLetter(char guess, ArrayList<Character> wordToGuessChars, boolean countMatches) {
		int wordLen = this.getCurrentWord().length();
		boolean letterMatched = false; // flag used to make sure all correct guessed will be register on correctMatches attribute
		for(int i = 0; i < wordLen ; i++) {
			if(guess == this.currentWord.charAt(i)) {
				wordToGuessChars.set(i, this.currentWord.charAt(i));
				if (countMatches)
					this.correctMatches++; // Increment correct match
				letterMatched = true;
			}
		}
		return letterMatched;
	}
	
	
	/** This method will draw the next phase of the hanged man **/
	public void nextPhase() {
		final int POINT = 1;
		this.hangingPhase = (this.hangingPhase + POINT); // Increment by 1 point
	}
	
	
	/** This method will skip to the next word from the game database and reset game progression values 
	 * 	the method will return true if a new word is available, else return false**/
	public boolean skipToNextWord() {
    	this.currentWord = db.getRandWord(); // take new word from DB
    	if (this.currentWord == null) {
    		return false; // if no word is available 
    	}
    	resetPhases();
    	return true;
	}
	
	
	/** This method will start a new playthrough**/
	public void newGame() {
		resetPhases();
		db.rebuildDB();
		currentWord = db.getRandWord();
	}
	
	
	/** This method will reset the game progression values **/
	public void resetPhases(){
		this.hangingPhase = 0;
		this.correctMatches = 0;
	}
	
	
	/** This method will verify a database of words exist **/
	public boolean verifyDB() {
		return db.getDBStatus();
	}
	
	// Getters and Setters methods:
	public int getCorrectMatches() {
		return correctMatches;
	}

	public void setCorrectMatches(int correctGuesses) {
		this.correctMatches = correctGuesses;
	}
	
	public String getCurrentWord() {
		return currentWord;
	}
	
	public void setCurrentWord(String currentWord) {
		this.currentWord = currentWord;
	}

	public int getHangingPhase() {
		return hangingPhase;
	}

	public void setHangingPhase(int hangingPhase) {
		this.hangingPhase = hangingPhase;
	}
	
	public int getMaxPhase() {
		return MAX_PHASE;
	}
	
	public Database getDB() {
		return db;
	}
}
