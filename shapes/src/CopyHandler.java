import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class CopyHandler implements EventHandler<MouseEvent>{
	protected ShapeCanvas canvas;
	private MyShape closestShape;
	private MyShape cloneShape;
	
	private double currX, currY;
	
	public CopyHandler(ShapeCanvas sc) {
		canvas = sc;
	}
	
	@Override
	public void handle(MouseEvent e) {
		if( e.getEventType().getName() == "MOUSE_PRESSED") {
			
			closestShape = canvas.closestShape(e.getX(), e.getY());
			cloneShape = (MyShape) closestShape.clone();
			
			canvas.addShape(cloneShape);
			
			currX = e.getX();
			currY = e.getY();
			
			cloneShape.setP1(closestShape.getP1());
			cloneShape.setP2(closestShape.getP2());

		}
		else if(e.getEventType().getName() == "MOUSE_DRAGGED") {
			
			double dx = e.getX() - currX;
            double dy = e.getY() - currY;
            
            cloneShape.move(dx, dy);
            
            currX = e.getX();
            currY = e.getY();
            
            canvas.paint();
		}
		else if(e.getEventType().getName() == "MOUSE_RELEASED") {
			CopyEdit copyEdit = new CopyEdit(canvas, cloneShape);
			canvas.addEdit(copyEdit);
			
			canvas.paint();
		}
	}
}
