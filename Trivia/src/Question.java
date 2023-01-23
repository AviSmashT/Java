import java.util.ArrayList;

/** This class represent a question with possible answers **/
public class Question {
	private String question;
	private  ArrayList<Answers> answers;
	final private int NUM_OF_ANSWERS = 4;
	
	
	public Question(){
		this.question = "";
		this.answers = new ArrayList<Answers>();
	}
	
	
	// Getters and Setters methods:
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public ArrayList<Answers> getAnswers(){
		return answers;
	}

	public void setAnswers(String answers, int index, boolean bool) {
		this.answers.add(new Answers(answers, bool));
	}
	
	
	/** Return atomic expression as a string */
	@ Override
	public String toString() {
		String answersString = "";
			for(int i = 0; i < NUM_OF_ANSWERS; i++)
				answersString += "Answer" + (i+1) + ": " + answers.get(i).getAnswer() + "\n";
		return "question: " + question + "\n" + answersString;
	}
	
}
