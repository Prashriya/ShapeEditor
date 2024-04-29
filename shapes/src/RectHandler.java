import javafx.scene.input.MouseEvent;

public class RectHandler extends DrawHandler{
	/**
	 * Constructor that calls parent class constructor
	 * @param cs
	 */
	public RectHandler(ShapeCanvas cs) {
		super(cs);
	}
	
	/**
	 * Handles the mouse pressed event
	 */
	@Override
	protected void mousePressed(MouseEvent e) {
		//setting new rectangle as current shape
	    shape = new Rect();

		//calling mousePressed method of parent class
		super.mousePressed(e);
	}
}