import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import java.util.Calendar;
import javax.swing.JOptionPane;


public class CalendarController {
	@FXML private Label meetingsLabel; // label for meetings details on current date
    @FXML private Label MeetingsTitle; // Label for meetings title on current date
	@FXML private Label titleLabel; // main title label
    @FXML private TextArea textArea;
	@FXML private GridPane daysGrid; // grid for dates buttons
	@FXML private Button editBtn; 
	@FXML private Button selBtn; 
	@FXML private Button addBtn; 
    @FXML private ComboBox<String> yearCB; // CB for years
    @FXML private ComboBox<String> monthCB; // CB for months
    private Button[][] daysBtn; // button array
	private int currentYear; // track calendar from buttons setup
	private int currentMonth; 
	private int currentDay; 
	private int dayOfWeek; // current day of the week
	private String currentKey; // current hash map key from button
    private CalendarData calData; 
    final private int DAYS = 7;
    final private int WEEKS = 6;
    final private int OFFSET = 1;
	final int FIRST_DAY_OF_THE_WEEK = 1;
	boolean inMonth = false; // this flag will indicate if the current day is in the same month as selected in the calendar - if not in month disable buttons

    @FXML
    public void initialize() {
    	calData = new CalendarData();
    	
    	// setup tracking of calendar
    	currentYear = calData.getCal().get(Calendar.YEAR);
    	currentMonth = calData.getCal().get(Calendar.MONTH);
    	currentDay = calData.getCal().get(Calendar.DATE);
    	
    	
    	comboBoxInit(); // initialize combobox
    	editState(false); // set edit state off 
    	checkAndUpdateTitleMonth(); // Update the title of the calendar
		currentYear = calData.getCal().get(Calendar.YEAR); // update year to be stored in key later
		initializeBtn(); // initialize buttons for calendar page
    	meetingsLabel.setWrapText(true); // make meetings label text wrap
    }


    private void initializeBtn() {
    	daysBtn = new Button[WEEKS][DAYS];

    	// set width and height fits pane grid:
    	double width = daysGrid.getPrefWidth() / DAYS; 
    	double height = daysGrid.getPrefHeight() / WEEKS;
    	for(int i = 0; i < WEEKS; i++) { // weeks
    		for(int j = 0; j < DAYS; j++) { // days
    			// update current date values for buttons text 
    			currentMonth = calData.getCal().get(Calendar.MONTH);
    			currentDay = calData.getCal().get(Calendar.DATE); 
    			dayOfWeek = calData.getCal().get(Calendar.DAY_OF_WEEK) - OFFSET; // update the day in the week for button text

    			// Create button:
        		daysBtn[i][j] = new Button(calData.getDAYS_IN_WEEK()[dayOfWeek] + "\n" + currentDay + " " + calData.getMONTHS_IN_YEAR()[currentMonth] ); // create new button with date data
        		daysBtn[i][j].setPrefSize(width, height); // set size of button
        		daysBtn[i][j].setStyle("-fx-font-size:10; -fx-font-weight: bold"); // button style
    	    	if (currentDay == FIRST_DAY_OF_THE_WEEK) { // if current day is the first day of the week, switch flag inMonth 
    	    		inMonth = !inMonth; 
    	    		if (inMonth == true) { // if in month AND in the first day of the week:
    	    			currentKey = (daysBtn[i][j].getText() + " - " + currentYear); // create the key for hash map - set as the first day of the month
    	    			updateMeetingTitle();  // update meetings section title
    	    		}
    	    	}
        		if (inMonth == true) // if not in month flag is on, don't disable button. else disable button
        			daysBtn[i][j].setDisable(false); 
        		else 
        			daysBtn[i][j].setDisable(true);
        		
          		daysBtn[i][j].setOnAction(new EventHandler<ActionEvent>() { // Create action event to button
    				@Override
    				public void handle(ActionEvent event) {
    					btnGridHandler(event);
    				}
    			}); // end of action event handler
        		daysGrid.add(daysBtn[i][j], j, i); // add configured button to grid  
        		calData.nextCal(); // move one day on calendar
    		} // end for days
    	} // end for weeks   
    	// Update the labels:
		updateMeetingTitle();
		updateMeetingsLabel();
    }
    
    
    
	/** This method will initialize the combobox with dates values **/
	private void comboBoxInit() {
		monthCB.getItems().addAll(calData.getMONTHS_IN_YEAR());
		yearCB.getItems().addAll("2020", "2021", "2022", "2023", "2024"); // years in CB
	}
    
	
	/** This method will handle date buttons press **/
    void btnGridHandler(ActionEvent event) {
    	Button btn = (Button)event.getSource(); // get the button event
    	currentKey = (btn.getText() + " - " + currentYear); // create key
    	updateMeetingTitle();
    	
    	// update the meetings label
    	if (calData.findByKey(currentKey) != null)
    		meetingsLabel.setText(calData.getMeeting(calData.findByKey(currentKey))); // update label with the found meeting value
    	else 
    		meetingsLabel.setText("");
    }
    
    
	/** This method will update the calendar buttons and labels when new month is submitted. **/
    @FXML
    void onSelDate(ActionEvent event) {
    	final int FIRST_DAY_OF_MONTH = 1;
		String yearStr = yearCB.getValue();
		String monthStr = monthCB.getValue();
		inMonth = false; // set in month as false to be switched when date is in the calendar selected month (to disable buttons in not in month)
		
		if (yearStr == null || monthStr == null)
			JOptionPane.showMessageDialog(null, "No date was selected.\nPlease select date before submiting");
		else {
			// convert year string taken from CB to integer
			int yearInt = 0;
			try {
				yearInt = Integer.valueOf(yearStr); 
			} catch (Exception error) {
				JOptionPane.showMessageDialog(null, "Error converting string to integer.\nThe program will close now.");
				System.out.println(error); 
				quit();
			}
			int monthInt = calData.monthNumerical(monthStr); // converting month to int
			
			calData.getCal().set(yearInt, monthInt , FIRST_DAY_OF_MONTH); /// 
			currentYear = calData.getCal().get(Calendar.YEAR);
	    	calData.goToFirstDayOfTheWeek(); // change date to the first day in the month
	    	checkAndUpdateTitleMonth(); // Update the title of the calendar
	    	
	    	// ReInitialize buttons
	    	for(int i = 0; i < WEEKS; i++) {
	    		for(int j = 0; j < DAYS; j++) {
	    			currentMonth = calData.getCal().get(Calendar.MONTH);
	    			currentDay = calData.getCal().get(Calendar.DATE);
	        		daysBtn[i][j].setText(calData.getDAYS_IN_WEEK()[j] + "\n" + currentDay + " " + calData.getMONTHS_IN_YEAR()[currentMonth]);
	    	    	if (currentDay == FIRST_DAY_OF_THE_WEEK) { // if current day is the first day of the week, switch flag inMonth 
	    	    		inMonth = !inMonth; 
	    	    		if (inMonth == true) { // if in month AND in the first day of the week:
	    	    			currentKey = (daysBtn[i][j].getText() + " - " + currentYear); // create the key for hash map - set as the first day of the month
	    	    			updateMeetingTitle();  // update meetings section title
	    	    		}
	    	    	}
	        		if (inMonth == true) // if not in month flag is on, don't disable button. else disable button
	        			daysBtn[i][j].setDisable(false);
	        		else 
	        			daysBtn[i][j].setDisable(true);
	        		calData.nextCal(); // move one day on calendar
	    		} // end days loop 
	    	} // end weeks loop
		} // end else
    	// Update the labels:
		updateMeetingTitle();
		updateMeetingsLabel();
    }


	/** This method will open TextArea field when 'Edit' button is pressed. **/
    @FXML
    void onEdit(ActionEvent event) {
    	editState(true); // set edit state on
    	textArea.setText(meetingsLabel.getText()); // update text area with previously inserted text 
    	
    }

    
	/** This method will update the current meeting value when 'Add' button is pressed. **/
    @FXML
    void onAdd(ActionEvent event) {	
    	calData.insertMeetings(currentKey, textArea.getText());
    	updateMeetingsLabel(); 
    	editState(false); // set edit state off 
    }
    
    
    
	/** This method will update the title label of the meetings section **/
	private void updateMeetingTitle() {
    	MeetingsTitle.setText( currentKey.substring(currentKey.indexOf("\n")+1)    );
	}

    
	/** This method will update label on the main title of the calendar **/
    private void updateMainTitle(String title) {
    	titleLabel.setText(title);
    }
    
    
	/** This method will update the title of the calendar showing month and year **/
    private void checkAndUpdateTitleMonth() {
    	final int FIRST_MONTH_OF_THE_YEAR = 0;
    	final int LAST_MONTH_OF_THE_YEAR = 11;
    	
    	// Update the current day and month 
    	currentDay = calData.getCal().get(Calendar.DATE);
		currentMonth = (calData.getCal().get(Calendar.MONTH));
	
	// Update title with the current month on calendar 
    if (currentDay == FIRST_DAY_OF_THE_WEEK) // if date at first day of the week - update current date
		updateMainTitle(calData.getMONTHS_IN_YEAR()[calData.getCal().get(Calendar.MONTH)] + " " + currentYear); 
	else if (currentMonth == LAST_MONTH_OF_THE_YEAR) // if date is not in the first day of the week and not at the last month of the year - update first month of the year
		updateMainTitle(calData.getMONTHS_IN_YEAR()[FIRST_MONTH_OF_THE_YEAR] + " " + currentYear); 
	else // else - update current date with 1 offset (the first button is the previous month)
		updateMainTitle(calData.getMONTHS_IN_YEAR()[calData.getCal().get(Calendar.MONTH) + OFFSET] + " " + currentYear);  // 
    }

    
    
    /** This method will set the buttons according to the edit state **/
    private void editState(boolean bool) {
    	if (bool) { // on edit state - textarea is visible and add button is enabled
        	textArea.setVisible(true); 
        	addBtn.setDisable(false); 
        	editBtn.setDisable(true); 
    	}else { // exit edit state - textarea is not visible and add button is disabled
        	textArea.setVisible(false); 
        	addBtn.setDisable(true); 
        	editBtn.setDisable(false); 
    	}
    }
    
    
    
	/** This method will update the meetings label of the selected day **/
    private void updateMeetingsLabel() {	
    	meetingsLabel.setText( calData.getMeeting(calData.findByKey(currentKey)) );
    }

    
    /**This method will quit and close the program**/
    private void quit() {
    	Platform.exit();
    }
}
