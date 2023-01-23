import javafx.scene.canvas.GraphicsContext;

/** This class represent the base graphics of the hang device **/
public class Base {
	// Attributes:
	private final int BASE_WIDTH = 4;
	private final int BASE_SUPPORT_WIDTH = 3;
	private final int DEFAULT_WIDTH = 1;
	// bottom part:
	private final int BOTTOM_X_POS_START = 60;
	private final int BOTTOM_X_POS_END = 160;
	private final int BOTTOM_Y_POS = 300;
	// middle part:
	private final int MIDDLE_X_POS = 110;
	private final int MIDDLE_Y_POS_START = 300;
	private final int MIDDLE_Y_POS_END = 100;
	// top part:
	private final int TOP_X_POS_START = 110;
	private final int TOP_X_POS_END = 210;
	private final int TOP_Y_POS = 100;
	// bottom support part:
	private final int BOTTOM_SUP_X_POS_START = 140;
	private final int BOTTOM_SUP_X_POS_START_REV = 80; // SECOND SUPPORT PART - reversed support part
	private final int BOTTOM_SUP_X_POS_END = 110;
	private final int BOTTOM_SUP_Y_POS_START = 300;
	private final int BOTTOM_SUP_Y_POS_END = 250;
	// top support part:
	private final int TOP_SUP_X_POS_START = 110;
	private final int TOP_SUP_X_POS_END = 140;
	private final int TOP_SUP_Y_POS_START = 130;
	private final int TOP_SUP_Y_POS_END = 100;

	
	/** This method will draw the base of the hang device **/
	public void draw(GraphicsContext gc) {
		// main axis width
		gc.setLineWidth(BASE_WIDTH); 
		gc.strokeLine(BOTTOM_X_POS_START, BOTTOM_Y_POS, BOTTOM_X_POS_END, BOTTOM_Y_POS);
		gc.strokeLine(MIDDLE_X_POS, MIDDLE_Y_POS_START, MIDDLE_X_POS, MIDDLE_Y_POS_END);
		gc.strokeLine(TOP_X_POS_START, TOP_Y_POS, TOP_X_POS_END, TOP_Y_POS);
		
		// support axis width:
		gc.setLineWidth(BASE_SUPPORT_WIDTH); 
		gc.strokeLine(BOTTOM_SUP_X_POS_START, BOTTOM_SUP_Y_POS_START, BOTTOM_SUP_X_POS_END, BOTTOM_SUP_Y_POS_END);
		gc.strokeLine(BOTTOM_SUP_X_POS_START_REV, BOTTOM_SUP_Y_POS_START, BOTTOM_SUP_X_POS_END, BOTTOM_SUP_Y_POS_END);
		gc.strokeLine(TOP_SUP_X_POS_START, TOP_SUP_Y_POS_START, TOP_SUP_X_POS_END, TOP_SUP_Y_POS_END); 
		
		gc.setLineWidth(DEFAULT_WIDTH); // return to default width
	}
}
