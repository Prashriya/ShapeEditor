import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class MoveHandler implements EventHandler<MouseEvent>{
	protected ShapeCanvas canvas;
	protected MyShape closestShape;
	private double x0, y0;
	private double prevX0, prevY0;
	
	/**
	 * constructor that takes ShapeCanvas object
	 * @param sc
	 */
	public MoveHandler(ShapeCanvas sc) {
		canvas = sc;
	}
	
	/**
	 * overrides handle method that handles MouseEvent on the canvas
	 */
	@Override
	public void handle(MouseEvent e) {

		if(e.getEventType().getName() == "MOUSE_PRESSED") {

			closestShape = canvas.closestShape(e.getX(), e.getY());
			
			x0 = e.getX();
			y0 = e.getY();
			
			prevX0 = x0;
			prevY0 = y0;
			
			
		}
		else if(e.getEventType().getName() == "MOUSE_DRAGGED") {	
			
			double prevX = x0;
			double prevY = y0;
			
			double dx = e.getX() - prevX;
            double dy = e.getY() - prevY;
            
            closestShape.move(dx, dy);
            
            x0 = e.getX();
            y0 = e.getY();

            
            canvas.paint(); 
		}
		else if(e.getEventType().getName() == "MOUSE_RELEASED") {
			
			double dx = e.getX() - prevX0;
            double dy = e.getY() - prevY0;
            
            MoveEdit moveEdit = new MoveEdit(canvas, closestShape, dx, dy);
            
            canvas.addEdit(moveEdit); 
		}

	}
	
}
