import javafx.scene.input.MouseEvent;

public class LineHandler extends DrawHandler{

	/**
	 * Constructor that calls parent class constructor
	 * @param cs
	 */
	public LineHandler(ShapeCanvas cs) {
		super(cs);
	}
	
	/**
	 * Handles the mouse pressed event
	 */
	@Override
	protected void mousePressed(MouseEvent e) {
		//creates a new line with the mouse coordinates
	    shape = new Line(e.getX(), e.getY(), e.getX(), e.getY());
	    
		//setting new line as current shape
	    canvas.setCurrentShape(shape);
	    
		//calling mousePressed method of parent class
	    super.mousePressed(e);
	}
	
}