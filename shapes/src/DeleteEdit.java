
public class DeleteEdit extends Edit{

	public DeleteEdit(ShapeCanvas c, MyShape s) {
		super(c, s);
	}
	
	@Override
	public void redo() {
		canvas.deleteShape(shape);
		canvas.paint();
	}
	
	@Override
	public void undo() {
		canvas.addShape(shape);
		canvas.paint();
	}
	
}
