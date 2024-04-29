import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DrawHandler implements EventHandler<MouseEvent>{
	
	protected MyShape shape;
	protected ShapeCanvas canvas;
	
	//constructor
	/**
	 * assigns ShapeCanvas sc as canvas
	 * @param sc
	 */
	public DrawHandler(ShapeCanvas sc){

		canvas = sc;
	}
	
	/**
	 * method that handles mouse pressed event
	 * @param e
	 */
	protected void mousePressed(MouseEvent e) {

		if(shape != null) {
			shape.setP1(e.getX(), e.getY());

			canvas.setCurrentShape(shape);
		}
	}
	
	/**
	 * method that handles mouse dragged event
	 * @param e
	 */
	protected void mouseDragged(MouseEvent e) {

		if(shape != null) {
			shape.setP2(e.getX(), e.getY());
			canvas.paint();
		}
	}
	
	/**
	 * method that handles mouse released event
	 * @param e
	 */
	protected void mouseReleased(MouseEvent e) {

		if(shape != null) {
			canvas.addShape(shape);
			canvas.setCurrentShape(null);
			
			DrawEdit drawEdit= new DrawEdit(canvas, shape);
			canvas.addEdit(drawEdit);

			canvas.paint();
		}
	}

	/**
	 * calls an appropriate method based on the type of mouse event
	 */
	@Override
	public void handle(MouseEvent event) {

		String eventName = event.getEventType().getName();
        System.out.println("Mouse Event: " + eventName);
        
        //switch statement based on the event name
        switch (event.getEventType().getName()) {
            case "MOUSE_PRESSED":
                mousePressed(event);
                break;
            case "MOUSE_DRAGGED":
                mouseDragged(event);
                break;
            case "MOUSE_RELEASED":
                mouseReleased(event);
                break;
            default:
            	break;
            	}
		}
}