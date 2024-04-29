import javafx.scene.input.MouseEvent;

public class OvalHandler extends DrawHandler{

	/**
	 * Constructor that calls parent constructor
	 * @param cs
	 */
	public OvalHandler(ShapeCanvas cs) {
		super(cs);
	}
	
	/**
	 * Handles the mouse Pressed event
	 */
	@Override
	protected void mousePressed(MouseEvent e) {
		//setting new oval as current shape
		shape = new Oval();
		
		//calling mousePressed method of parent class
		super.mousePressed(e);
	}
}