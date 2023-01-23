import javafx.scene.canvas.GraphicsContext;

/** This Class represent the graphics of the hanged man phases in the game **/
public class Hanging {
	// Attributes:
	private final int HEAD_WIDTH = 2; // head stroke width
	private final int MOUTH_WIDTH = 1; // head stroke mouth
	private final int ROPE_WIDTH = 1; // head stroke rope
	
	// rope:
	private final int ROPE_X_POS = 200;
	private final int ROPE_Y_POS_START = 100;
	private final int ROPE_Y_POS_END = 150;

	// head:
	private final int HEAD_X_POS = 187;
	private final int HEAD_Y_POS = 150;
	private final int HEAD_Y_HEIGHT = 25;
	private final int HEAD_Y_WIDTH = 25;
	
	// torso:
	private final int TORSO_X_POS = 200;
	private final int TORSO_Y_POS_START = 176;
	private final int TORSO_Y_POS_END = 200;

	// arms:
	private final int ARM_X_POS_START = 200;
	private final int LARM_X_POS_END = 185; // LEFT ARM
	private final int RARM_X_POS_END = 215; // RIGHT ARM
	private final int ARM_Y_POS_START = 180;
	private final int ARM_Y_POS_END = 190;
	
	// legs:
	private final int LEG_X_POS_START = 200;
	private final int LLEG_X_POS_END = 185; // LEFT ARM
	private final int RLEG_X_POS_END = 215; // RIGHT ARM
	private final int LEG_Y_POS_START = 180 + 19; 
	private final int LEG_Y_POS_END = 190 + 19;

	// mouth:
	private final int MOUTH_X_POS_START = 198;
	private final int MOUTH_X_POS_END = 202;
	private final int MOUTH_Y_POS = 170;

	// left eyes:
	private final int EYE_X_POS_START = 198;
	private final int LEYE_X_POS_END = 196; // LEFT ARM
	private final int REYE_X_POS_END = 200; // RIGHT ARM
	private final int EYE_Y_POS_START = 161;
	private final int EYE_Y_POS_END = 163;
	private final int EYE_OFFSET = 2;
	private final int EYE_RIGHT = 7;

	/** This method will draw the hanged man with the appropriate phase 
	 *  depending on the parameter gamePhase.**/
	public void draw(GraphicsContext gc, int gamePhase) {
		switch(gamePhase){
			case 1:
				rope(gc);
				break;
			case 2:
				head(gc);
				break;
			case 3:
				torso(gc);
				break;
			case 4:
				leftArm(gc);
				break;
			case 5:
				rightArm(gc);
				break;
			case 6:
				leftLeg(gc);
				break;
			case 7:
				rightLeg(gc);
				break;
			case 8:
				mouth(gc);
				break;
			case 9:
				LeftEye(gc);
				break;
			case 10:
				rightEye(gc);
				break;
			default:
				break;
		}
	}
	
	
	private void rope(GraphicsContext gc) {
		gc.setLineWidth(ROPE_WIDTH);
		gc.strokeLine(ROPE_X_POS, ROPE_Y_POS_START, ROPE_X_POS, ROPE_Y_POS_END);
	}

	private void head(GraphicsContext gc){
		gc.setLineWidth(HEAD_WIDTH);
		gc.strokeOval(HEAD_X_POS, HEAD_Y_POS, HEAD_Y_HEIGHT, HEAD_Y_WIDTH);
	}
	private void torso(GraphicsContext gc){
		gc.setLineWidth(HEAD_WIDTH);
		gc.strokeLine(TORSO_X_POS, TORSO_Y_POS_START, TORSO_X_POS, TORSO_Y_POS_END);
	}
	private void leftArm(GraphicsContext gc){
		gc.setLineWidth(HEAD_WIDTH);
		gc.strokeLine(ARM_X_POS_START, ARM_Y_POS_START, LARM_X_POS_END, ARM_Y_POS_END);
	}
	private void rightArm(GraphicsContext gc){
		gc.setLineWidth(HEAD_WIDTH);
		gc.strokeLine(ARM_X_POS_START, ARM_Y_POS_START, RARM_X_POS_END, ARM_Y_POS_END);
	}
	private void leftLeg(GraphicsContext gc){
		gc.setLineWidth(HEAD_WIDTH);
		gc.strokeLine(LEG_X_POS_START, LEG_Y_POS_START, LLEG_X_POS_END, LEG_Y_POS_END);
	}
	private void rightLeg(GraphicsContext gc){
		gc.setLineWidth(HEAD_WIDTH);
		gc.strokeLine(LEG_X_POS_START, LEG_Y_POS_START, RLEG_X_POS_END, LEG_Y_POS_END);
	}
	private void mouth(GraphicsContext gc){
		gc.setLineWidth(MOUTH_WIDTH);
		gc.strokeLine(MOUTH_X_POS_START, MOUTH_Y_POS, MOUTH_X_POS_END, MOUTH_Y_POS);
	}
	
	private void LeftEye(GraphicsContext gc){
		gc.setLineWidth(MOUTH_WIDTH);
		gc.strokeLine(EYE_X_POS_START, EYE_Y_POS_START, LEYE_X_POS_END, EYE_Y_POS_END);
		gc.strokeLine(EYE_X_POS_START-EYE_OFFSET, EYE_Y_POS_START, REYE_X_POS_END-EYE_OFFSET, EYE_Y_POS_END); // -2 OFF SET TO MAKE X
	}
	private void rightEye(GraphicsContext gc){
		gc.setLineWidth(MOUTH_WIDTH);
		gc.strokeLine(EYE_X_POS_START+EYE_RIGHT, EYE_Y_POS_START, LEYE_X_POS_END+EYE_RIGHT, EYE_Y_POS_END);
		gc.strokeLine(EYE_X_POS_START-EYE_OFFSET+EYE_RIGHT, EYE_Y_POS_START, REYE_X_POS_END-EYE_OFFSET+EYE_RIGHT, EYE_Y_POS_END); // -2 OFF SET TO MAKE X
	}
}
