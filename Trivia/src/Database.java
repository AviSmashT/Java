import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/** This class will read trivia data from a file. For each 5 lines, the fires line is the question
 *  and the next 4 lines are the possible answer, while the correct answer is the first answer in the file.
 *  The data will be saved in ArrayList of type Questions **/
public class Database {
	private boolean DBStatus; // database status - active/inactive
	private File file; 
	private ArrayList<Question> questionsList; // list of all questions read from file
	private int numberOfQuestions; // question counter 
	private final int SIZE_OF_QUESTION = 4; // question size in the array (5 elements in the array)
	private final int QUESTION_START = 0; // the starting element of an array
	
	public Database() {
    	this.file = new File("triviaQuiz.txt");
    	this.numberOfQuestions = 0;
    	this.questionsList = new ArrayList<Question>();
    	this.DBStatus = createDB() ;
	}
	
	/** This method will read from a file and write the data back to an ArrayList */
	private boolean createDB() {
		if(checkFile()) {
			try {
				int index = QUESTION_START;
				Scanner fileScanner = new Scanner(file);
				while(fileScanner.hasNextLine()) { 
						this.questionsList.add(new Question()); // if the first element of a question - create new Question object
						this.questionsList.get(this.numberOfQuestions).setQuestion(fileScanner.nextLine()); // add question to object from file
						for (index = 0; index < SIZE_OF_QUESTION; index++) { // loop to add answers from file
							if(index == QUESTION_START) {
								this.questionsList.get(this.numberOfQuestions).setAnswers(fileScanner.nextLine(), index, true); // NOTE: set the correct answer
							}
							else
								this.questionsList.get(this.numberOfQuestions).setAnswers(fileScanner.nextLine(), index, false);
						}
						index = QUESTION_START; // reset index (for adding next question)
						this.numberOfQuestions++; // Increment number of questions
				}
				fileScanner.close();
				return true;
			} catch (IOException e) {
				System.out.println("Error! File is inaccessible."); // unable to read
				return false;
			}
		}
		else
			return false;
	}
	
	
	/** This method will return true if the file fit the database format, else return false */
	private boolean checkFile() {
		int lines = 0;
		try (Scanner fileScanner = new Scanner(file)) {
				while(fileScanner.hasNextLine()) {
					fileScanner.nextLine();
					lines++; // count lines in file
				}
			}
			catch (IOException e){
				System.out.println("Error! File is inaccessible.");
				return false;
			}
		
		if ((lines % 5) == 0) // check 5 lines for each question
			return true;
		else {
			System.out.println("Error! Data format on file.");
			return false;
		}
	}
	
	
	// Getters and Setters methods:
	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}
	
	public ArrayList<Question> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(ArrayList<Question> questionsList) {
		this.questionsList = questionsList;
	}
	
	public boolean getDBStatus() {
		return DBStatus;
	}
}
