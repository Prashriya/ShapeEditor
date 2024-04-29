import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DeleteHandler implements EventHandler<MouseEvent>{
	protected ShapeCanvas canvas;

	public DeleteHandler(ShapeCanvas sc) {
		canvas = sc;
	}

	@Override
	public void handle(MouseEvent e) {
		if(e.getEventType().getName() == "MOUSE_CLICKED") {

			MyShape shape = canvas.closestShape(e.getX(), e.getY());
			canvas.deleteShape(shape);

			canvas.setCurrentShape(null);	
			
			
			
			DeleteEdit deleteEdit = new DeleteEdit(canvas, shape);
			canvas.addEdit(deleteEdit);
			
			canvas.paint();
		}
	}
}
