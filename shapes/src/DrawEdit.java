
public class DrawEdit extends Edit{

	public DrawEdit(ShapeCanvas c, MyShape s) {
		super(c, s);
	}
	
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
