import javax.swing.JOptionPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class TriviaController {
    @FXML private Label levelLabel; // display level on screen
    @FXML private Label scoreLabel; // display score
    @FXML private Button skipBtn; // skip button
    @FXML private Button conBtn;  // continue button
    @FXML private Button restBtn; // restart button
    @FXML private Button submitBtn; // submit button
    @FXML private GridPane triviaData; // contains answers and questions
    @FXML private GridPane userInputGrid; // contains user choice buttons
    private StartGame game;
    final private int POINT = 1;
    final private int NOT_SELECTED = -1;
    final private int NUM_OF_ANSWERS = 4;
    final private String WORONG_ANSWER_BTN = "-fx-background-color: #C70039; -fx-text-fill: white; -fx-font-weight: bold";
    final private String CORREECT_ANSWER_BTN = "-fx-background-color: #117A65; -fx-text-fill: white; -fx-font-weight: bold";

    
    @FXML
    public void initialize() {
    	game = new StartGame();
    	if(game.verifyDB()) { // if database available
        	updateScreen();
        	conBtn.setVisible(false); // make continue button unavailable
    	}else {
    		JOptionPane.showMessageDialog(null, "Error! There is a problem with the questions file.\n"
    				+ "Make sure the file is in the right path and with the correct data format");
    		quit();
    	}
    }
    
    @FXML
    void OnSubmt(ActionEvent event) {
    	if (toggledBtn() == NOT_SELECTED) { // if no answer was selected by player (-1)
    		JOptionPane.showMessageDialog(null, "No answer was selected.\nPlease select an answer before submitting.");
    		return;
    	}
    	else if(game.getCorrectAnswer() == toggledBtn()) { // answer is correct
			JOptionPane.showMessageDialog(null, "You are correct!");
			game.setScore(game.getScore() + POINT); // Increment score
    	}else { // answer is wrong
			JOptionPane.showMessageDialog(null, "You are wrong!");
    		userInputGrid.getChildren().get(toggledBtn()).setStyle(WORONG_ANSWER_BTN); // change style to the wrong answer button chosen by the player
    	}
    	// reveal state:
		userInputGrid.getChildren().get(game.getCorrectAnswer()).setStyle(CORREECT_ANSWER_BTN); // change style to the correct answer button
    	revealState(true); // set reveal state
    } 
    
    
    /** This method will continue to the next question when continue button is clicked. **/
    @FXML
    void conBtnHandler(ActionEvent event) {
    	revealState(false); // exit reveal state
    	game.nextQuestion(); // fetch next question
    	updateScreen(); // update the labels accordingly
    	if(game.getCurrentLevel() > game.getlevels()){ // end game if there are no more levels
    		if(gameEnded(event))
    			quit();
    	}
    }

    
    /** This method will restart the game when the restart button is clicked. **/
    @FXML
    void restartBtnHandler(ActionEvent event) {
		game.restartGame();
		updateScreen();
    }
    
    
    /** This method will skip to the next question when the skip button is clicked. **/
    @FXML
    void skipBtnHandler(ActionEvent event) {
    	game.nextQuestion();
		updateScreen();
    	if(game.getCurrentLevel() > game.getlevels()){ // end game if there are no more levels
    		if(gameEnded(event))
    			quit();
    	}
    }
    
    
    /** This method will exit the game when the quit button is clicked. **/
    @FXML
    void quitBtnHandler(ActionEvent event) {
    	quit();
    }
    
    
    /** When user toggle a button, this method will make sure the other buttons are untoggled*/
    @FXML
    void onChoice(ActionEvent event) {
    	if (!((ToggleButton)event.getTarget()).isSelected()) // if the pressed button is already toggled, untoggled the button
    		((ToggleButton)event.getTarget()).setSelected(false);
    	else {
	    	for(Node input: userInputGrid.getChildren()) { // loop on grid pane items and untoggled buttons
	        	if((input instanceof ToggleButton) && ((ToggleButton) input).isSelected())
	        		((ToggleButton) input).setSelected(false);
	    	}
	    	((ToggleButton)event.getTarget()).setSelected(true); // toggle the event button
    	}
    }
    
    
    /** This method set buttons on disable on answer revel state 
     *  and set buttons back on enable when out of answer revel state. **/
    private void revealState(boolean bool) {
    	conBtn.setVisible(bool); // continue button
    	skipBtn.setDisable(bool); // skip button
    	submitBtn.setDisable(bool); // submit button
    	restBtn.setDisable(bool); // restart button
    }
    
    
    /** This method will un-toggle all answers buttons. **/
    private void untoggle() {
    	for(Node input: userInputGrid.getChildren()) { // loop on grid pane items and untoggled buttons
        	if((input instanceof ToggleButton) && ((ToggleButton) input).isSelected())
        		((ToggleButton) input).setSelected(false);
    	}
    }
    
    
    /** This method will update the screen labels and with game progression. **/
    private void updateScreen() {
    	int i = 0;
    	final int OFFSET = 1;
    	scoreLabel.setText(game.getScore() + ""); // update player score
    	levelLabel.setText(game.getCurrentLevel() + "/" + game.getlevels()); // update levels
    	((Labeled) triviaData.getChildren().get(i)).setText(game.getCurrentQuestion()); // update new question 
    	((Labeled) triviaData.getChildren().get(i)).setWrapText(true); // set question text to wrap
    	for (i = 0; i < NUM_OF_ANSWERS; i++)  // loop 0 to number of answers to update all answers labels 
        	((Labeled) triviaData.getChildren().get(i+OFFSET)).setText(game.getCurrentAnswerList().get(i).getAnswer()); 
        for(i = 1; i <= NUM_OF_ANSWERS; i++) // set default styling to all answers buttons
        	userInputGrid.getChildren().get(i).setStyle(null);
    	untoggle(); // un-toggle all selected buttons
    }
    
    
    /**This method will return the index of the toggled button
     * if no button is selected, return -1*/
    private int toggledBtn() {
    	int answerNum = 0; // index
    	for(Node input: userInputGrid.getChildren()) { // loop on Grid pane of all buttons
        	if((input instanceof ToggleButton) && ((ToggleButton) input).isSelected()) // fetch the selected button
        		return answerNum;
        	else
        		answerNum++;
    	}
		return NOT_SELECTED; // if no button is selected, return -1
    }
    
    
	/** This method will show a message dialog on screen promoting player to keep playing.
	 *  The method will return false and update the game accordingly if player choose yes. else, return true. **/
    private boolean gameEnded(ActionEvent event) {
		int userInput = JOptionPane.showConfirmDialog(null, "Your score is: " + game.getScore() + "/" + game.getlevels() + 
				"\nDo you want to keep playing?", "Message" ,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if((userInput == 0)) { // continue playing and restart game
			game.restartGame();
			updateScreen();
			return false;
		}else { // quit game
			JOptionPane.showMessageDialog(null, "You finished the game!");
			return true;
		}
    }
   
   
    /**This method will quit and close the game**/
    private void quit() {
    	Platform.exit();
    }
}

