
/** This class represent answer with string and boolean value **/
public class Answers {
	private String answer;
	private boolean boolValue;
	

	public Answers(String answer, boolean boolValue){
		this.answer = answer;
		this.boolValue = boolValue;
	}
	
	
	// Getters and Setters methods:
	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
	public boolean getBoolValue() {
		return boolValue;
	}

	public void setBoolValue(boolean boolValue) {
		this.boolValue = boolValue;
	}
	
	
}
