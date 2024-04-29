
public class CopyEdit extends Edit{

	/**
	 * constructor that take canvas and shape
	 * @param c
	 * @param s
	 */
	public CopyEdit(ShapeCanvas c, MyShape s) {
		super(c, s);
	}
	
	/**
	 * overriding the redo method  
	 */
	@Override
	public void redo() {
		canvas.addShape(shape);
		
		canvas.paint();
	}
	
	@Override
	public void undo() {
		canvas.deleteShape(shape);
		canvas.paint();
	}
	
}
