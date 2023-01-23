import java.util.ArrayList;
import javax.swing.JOptionPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class HangmanController {
    @FXML private Canvas canv;
    @FXML private Label lettersLabel; // this label contains the letters used (only miss match)
    @FXML private Label wordToGuess; // this is the word the player needs to guess
    @FXML private Button submitBtn; 
    @FXML private Button nextWordBtn;
    @FXML private Button resetBtn;
    @FXML private ComboBox<Character> lettersCBox = new ComboBox<Character>();
    private ArrayList<Character> wordToGuessChars; // this array will be updated with indication of player guess (to be displayed on the wordToGuess label)
    private GraphicsContext gc;
	private StartGame game;
	private Graphics graphics;
	private int currentWordLength;

    public void initialize() {
    	gc = canv.getGraphicsContext2D();
    	game = new StartGame();
    	graphics = new Graphics(gc);
    	if(game.verifyDB()) { // if database available
        	wordToGuessChars = new ArrayList<Character>();
        	comboBoxInit(); // initialize combobox with letters
        	initializeWordLabel(); // update label with the word to guess
    	}else {
    		popupMessage( "Error! There is a problem with the words file.\n"
    				+ "Make sure the file is in the right path and with the correct data format");
    		Platform.exit();
    		quit();
    	}
    }
    
    @FXML
    void onSubmit(ActionEvent event) {
    	Character usedLetter = comboboxHandler(); // get letter from combobox and remove used letter
    	if (usedLetter == null) { // if no letter was selected before submitting
			popupMessage("Please select a letter before submiting.");
    		return;
    	}
		if(game.checkLetter(usedLetter, wordToGuessChars, true)) { /*** if guess is correct ***/
			updateWordLabel(); // update labels on screen accordingly
			if(game.getCorrectMatches() == currentWordLength) { // if word fully guessed
				popupMessage("Your guess is correct!\nThe word is - " + game.getCurrentWord());
	    		if( game.skipToNextWord()) { // if player wants to keep playing, skip to next word from DB
	    			promoteContinue();
	    		}else {
	    			popupMessage("You Finished the game!");
	    			promoteNewGame(); // promote keep playing
	    		}
			}
	    }
		else { /*** if guess is wrong ***/
			updateHangedMan(); // update the hanged man graphics
			if(game.getHangingPhase() == game.getMaxPhase()) { // if player reach final hanging phase - gameover
				popupMessage("You DIED!");
				promoteContinue(); // promote keep playing 
		    }
		}
    }
    
    /**This method will skip to the next word**/
    @FXML
    private void onNextWordBtn(ActionEvent event) {
    	if(game.skipToNextWord()) { // if there is a new word in the DB, update screen and reset game progression
    		restartLevel();
    	}else {// if there is no longer new words in DB - max phase is 10 - promote new game
			popupMessage("You Finished the game!");
			promoteNewGame();
    	}
    }
    
    
    @FXML
    void onReset(ActionEvent event) {
    	restartLevel();
    }
    
    
    /** This method will initialize the word to guess array and update word
     *  to guess label with indication of the word length **/
    private void initializeWordLabel() {
    	updateWordLength(); 
    	wordToGuess.setText(""); 
    	wordToGuessChars.clear(); 
    	for(int i = 0; i < currentWordLength; i++ ) {
    		wordToGuessChars.add('_');
        	wordToGuess.setText(wordToGuess.getText() + " " + wordToGuessChars.get(i));
    	}
	}
    
    
    /** This method will update the word to guess label with letter guessed**/
    private void updateWordLabel() {
    	wordToGuess.setText("");
    	for(int i = 0; i < currentWordLength; i++ ) {
        	wordToGuess.setText(wordToGuess.getText() + " " + wordToGuessChars.get(i));
    	}
	}
    
    
    /** This method will update hanging phase and draw hanging phase **/
    private void updateHangedMan() {
		game.nextPhase(); // Increment hanging phase counter
		try {
			graphics.draw(game.getHangingPhase()); // draw next hanging phase
		} catch (InValidPhaseException e) {
			e.printStackTrace();
		} 
    }
    
 
    /** This method will handle the combobox letter submit. If letter is selected,
     *  it will be removed from the combobox and added to letters label.
     *  The method will return the letter selected, else, return null **/
    private Character comboboxHandler() {
    	Character letter = lettersCBox.getValue();
	    if (letter != null) {
	    	if(!game.checkLetter(letter, wordToGuessChars, false)) // if word is a match, don't add to used letters label
	    		lettersLabel.setText(lettersLabel.getText() + "\n" + letter); // update letter used on the screen (label)
	    	lettersCBox.getItems().remove(lettersCBox.getValue()); // remove letter used from combobox
			return letter;
	    }
	    else
	    	return null; // if nothing selected by user
    }
    
    
    /** This method will restart game progression and screen **/
    private void restartLevel() {
		lettersLabel.setText(""); // reset letter label
		initializeWordLabel(); // reset word to guess label
		comboBoxInit(); // reset combobox
		game.resetPhases(); // reset hanging phase
		graphics.clearMan(); // clear screen hanging
    }

    
	/** This method will initialize the combobox with the abc letters **/
	private void comboBoxInit() {
		final int LETTERS = 26;
		lettersCBox.getItems().clear(); // clear array from reaming letter 
		char asciiLetter = 'a';
		for(int i = 0; i < LETTERS; i++) { // 26 letters
			lettersCBox.getItems().add(asciiLetter);
			asciiLetter++;
		}
	}
	
	
	/** This method will show a message dialog on screen. 
	 * 	The message is string given as parameter**/
	private void popupMessage(String mes) {
		JOptionPane.showMessageDialog(null, mes);
	}
	
	
	/** This method will show a message dialog on 
	 * 	screen promoting player to keep playing.**/
	private void promoteContinue() {
		int choice =  JOptionPane.showConfirmDialog(null, "Do you want to keep playing?", "Message" ,JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE);
		if (choice == 0) {
			restartLevel(); // restart level (same word)
		}
	    else
	    	quit(); // quit game
	}
	
	/** This method will show a message dialog on 
	 * 	screen promoting a new game **/
	private void promoteNewGame() {
		int choice = JOptionPane.showConfirmDialog(null, "Do you want to start a new playthrough?", "Message" ,JOptionPane.YES_NO_OPTION,
	               JOptionPane.QUESTION_MESSAGE);
		
		if (choice == 0) {
			game.newGame();
			restartLevel();
		}
		else
			quit();
	}	
	
    
	
    /**This method will update the word length**/
	private void updateWordLength() {
		currentWordLength = game.getCurrentWord().length();
	}
    
	
    /**This method will quit and close the game**/
    private void quit() {
	    Platform.exit();
    }
}

