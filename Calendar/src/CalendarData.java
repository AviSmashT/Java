import java.util.Calendar;
import java.util.HashMap;


public class CalendarData {
	// Attributes:
    Calendar cal = Calendar.getInstance();
	final private String[] DAYS_IN_WEEK = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	final private String[] MONTHS_IN_YEAR = {"January","February","March","April","May","June","July", "August","September","October","November","December"};
	final private int FIRST_DAY_OF_WEEK = 1;
	final private int FIRST_DAY_OF_MONTH = 1;
	final private int ONE_DAY = 1;
	private HashMap<String, String> meetingsHash; // meeting string?



	// Contractor:
	public CalendarData() {
		cal.set( cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), FIRST_DAY_OF_MONTH); // set the current date on calendar instance
		goToFirstDayOfTheWeek();
		meetingsHash = new HashMap<String, String>(); // hashmap of meetings
	}


	// Methods:
	/** This method will pass the date to the next coming day **/
	public void nextCal() {
		cal.add(Calendar.DAY_OF_MONTH, ONE_DAY); // add one day to date
	}
	
	
	/** This method will change the date backwards to the first day in the week. **/
	public void goToFirstDayOfTheWeek() {
		while (cal.get(Calendar.DAY_OF_WEEK) != FIRST_DAY_OF_WEEK) // do while day is not the first day of the week
			cal.add(Calendar.DAY_OF_MONTH, - ONE_DAY); // backwards one day
	}
	
	
	/** This method will return the numerical value of a month **/
	public int monthNumerical(String monthPara) {
		int index = 0; // index to be returned 
		
		for(String month : MONTHS_IN_YEAR) {
			if (month == monthPara) { // if the month name is match - break and return index
				break;
			}
			index++;
		}
		return index;
	}

	
	/** Given a key this method will create a new meetings descriptions on the hash map.
	 *  If meeting description is already exist - update description that fits the given key **/
	public void insertMeetings(String key, String descriptions) {
		String meet = findByKey(key);
		if (meet == null) // if meetings dose not exist on hash map
			meetingsHash.put(key, descriptions); // add descriptions
		else
			meetingsHash.put(meet , descriptions); // edit descriptions
	}
	
	
	/** Given a key, this method will return the value, if key was not found - return null**/
	public String findByKey(String key) { 
		for (String meet : meetingsHash.keySet()) {
			if (meet.equals(key))
				return meet;
		}
		return null;
	}
	
	

	// Setters and Getters:
	public String[] getDAYS_IN_WEEK() {
		return DAYS_IN_WEEK;
	}
	public String[] getMONTHS_IN_YEAR() {
		return MONTHS_IN_YEAR;
	}
    public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	public String getMeeting(String key) {
		return meetingsHash.get(key);
	}
	public HashMap<String, String> getMeetingsHash() {
		return meetingsHash;
	}
	
}
