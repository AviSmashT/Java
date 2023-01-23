import java.util.ArrayList;
import java.util.Collections;

/** This Class represent Trivia game **/
public class StartGame {
	private String currentQuestion; 
	private ArrayList<Answers> currentAnswerList; 
	private Database db;
	private int currentLevel; // current level (current question number)
	private int score; // player score
	private int levels; // total levels in the game (number of questions)
	final private int OFFSET = 1;


	public StartGame() {
		db = new Database(); // create new database with from file
		this.currentLevel = 1;
		this.score = 0;
		if (verifyDB()) {
			this.levels = db.getNumberOfQuestions();
			shuffleQuestions(); // Shuffle currentQuestion
			updateNewQuestion(); // update currentQuestion and currentAnswerList
			shuffleCurrentAnswers(); // Shuffle currentAnswerList
		}
	}
	
	
	/** This method will return true if user guess is correct, else return false **/
	public boolean checkAnswer(int userGuess) {
		if (userGuess == getCorrectAnswer()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/** This method will update the game progress if it's time for the next question **/	
	public void nextQuestion() {
		this.currentLevel++; // update level number
		if (currentLevel <= levels) { // update next question only if in levels range
			updateNewQuestion();
		}
		shuffleCurrentAnswers();
	}
	
	/** This method will reset the game progress (start new game) **/
	public void restartGame() {
		this.currentLevel = 1;
		this.score = 0;
		shuffleQuestions();
		updateNewQuestion();
		shuffleCurrentAnswers();
	}
	
	
	/* This method will update the attributes with new question and answers from the database**/
	private void updateNewQuestion() {
		this.currentQuestion = db.getQuestionsList().get(currentLevel-OFFSET).getQuestion();
		this.currentAnswerList = db.getQuestionsList().get(currentLevel-OFFSET).getAnswers();
	}
	
	
	// Shuffle methods:
	/** Shuffle questions ArrayList**/
	private void shuffleQuestions() {
		Collections.shuffle(this.db.getQuestionsList()); // shuffle list
	}
	
	/** Shuffle answers ArrayList**/
	private void shuffleCurrentAnswers() {
		Collections.shuffle(this.currentAnswerList); // shuffle list
	}
	
	/** This method will verify a database of words exist **/
	public boolean verifyDB() {
		return db.getDBStatus();   	
	}
	
	/** This method will return the number of the correct answer **/
	public int getCorrectAnswer() {
		int index = 0;
		for(Answers answer: currentAnswerList) {
			if (answer.getBoolValue() == true)
				break;
			index++;
		}
		return index+OFFSET;
	}
	
	
	// Getters and Setters methods:

	public int getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(int level) {
		this.currentLevel = level;
	}
	
	
	public int getlevels() {
		return levels;
	}

	public void setlevels(int numOfQuestions) {
		this.levels = numOfQuestions;
	}
	
	
	public ArrayList<Answers> getCurrentAnswerList() {
		return currentAnswerList;
	}


	public void setCurrentAnswerList(ArrayList<Answers> currentAnswerList) {
		this.currentAnswerList = currentAnswerList;
	}
	
	public String getCurrentQuestion() {
		return currentQuestion;
	}


	public void setCurrentQuestion(String currentQuestion) {
		this.currentQuestion = currentQuestion;
	}

	public int getScore() {
		return score;
	}
	
	public int setScore(int score) {
		return this.score = score;
	}
}
