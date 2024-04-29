import java.util.ArrayList;

public class GroupEdit extends Edit{
	private ArrayList<MyShape> members;
	
	public GroupEdit(ShapeCanvas c, MyShape s) {
		super(c, s);
		members = ((ShapeGroup) s).getMembers();
	}
	
	@Override
	public void undo() {
		
		for(MyShape  s: members) {
			canvas.addShape(s);
		}
		
		canvas.deleteShape(shape);
		canvas.setCurrentShape(null);
		canvas.paint();
	}
	
	@Override
	public void redo() {
		
		for(MyShape  s: members) {
			canvas.deleteShape(s);
		}
		
		canvas.addShape(shape);
		canvas.paint();
	}
}
