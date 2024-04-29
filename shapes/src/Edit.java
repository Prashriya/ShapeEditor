/**
 * This class shows editing of a shape
 */
public abstract class Edit {
	
	protected MyShape shape;
	protected ShapeCanvas canvas;
	
	public Edit(ShapeCanvas c, MyShape s) {

		
		shape = s;
		canvas = c;
	}
	
	public abstract void undo();
	
	public abstract void redo();
}
