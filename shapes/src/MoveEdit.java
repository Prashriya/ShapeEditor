
public class MoveEdit extends Edit{
	private double dx, dy;

	public MoveEdit(ShapeCanvas c, MyShape s, double dx, double dy) {
		super(c, s);
		
		this.dx = dx;
		this.dy = dy;
	}
	
	@Override
	public void redo() {
		shape.move(dx, dy);
		canvas.paint();
	}
	
	@Override
	public void undo() {
		shape.move(-dx, -dy);
		canvas.paint();
	}
	
}
