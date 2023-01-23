import javafx.scene.canvas.GraphicsContext;

/** This class represent the graphics in the Hangman game **/
public class Graphics {
	// Attributes:
	private Base base;
	private Hanging hanging;
	private GraphicsContext gc;
	private final int MAX_PHASE = 10;
	private final int MIN_PHASE = 1;
	
	
	// clear method coordinates:
	private final int CLEAR_RANGE_X_START = 143;
	private final int CLEAR_RANGE_Y_START = 103;
	private final int HANGING_RANGE_WIDTH = 110;
	private final int HANGING_RANGE_HIGHET = 111;

	
	public Graphics(GraphicsContext gc) {
		this.gc = gc;
    	this.base = new Base();
    	this.hanging = new Hanging();
    	this.base.draw(gc);
	}
	
	/** Draw a graphic object **/
	public void draw(int hangingPhase) throws InValidPhaseException { // if hangingPhase not in range throw exception
		if (hangingPhase >= MIN_PHASE && hangingPhase <= MAX_PHASE)
			this.hanging.draw(this.gc, hangingPhase); 
		else 
			throw new InValidPhaseException();
	}
	
	public void clearMan() {
		this.gc.clearRect(CLEAR_RANGE_X_START, CLEAR_RANGE_Y_START, HANGING_RANGE_WIDTH, HANGING_RANGE_HIGHET); // clear canvas 
	}
}
