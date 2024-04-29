import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GroupHandler implements EventHandler<MouseEvent> {
	private ShapeCanvas canvas;
	private ShapeGroup group;

	public GroupHandler(ShapeCanvas sc) {
		canvas = sc;
	}

	protected void mousePressed(MouseEvent e) {
		group = new ShapeGroup();
		
		canvas.setCurrentShape(group);
		
		group.setP1(e.getX(), e.getY());

	}
	
	protected void mouseDragged(MouseEvent e) {
	
		group.setP2(e.getX(), e.getY());
		canvas.paint();
		
	}

	protected void mouseReleased(MouseEvent e) {
		
		ArrayList<MyShape> within = new ArrayList<>();
		
		for(MyShape s : canvas.getShape()) {
			if(group.within(s)) {
				
				group.addMember(s);
				within.add(s);
			}
		}
		
		for(MyShape s : within) {
			canvas.deleteShape(s);
		}
		
		if(group != null) {
			canvas.addShape(group);
		}
		
		GroupEdit groupEdit = new GroupEdit(canvas, group);
		canvas.addEdit(groupEdit);
		
		group.setP2(e.getX(), e.getY());
		canvas.paint();

	}

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
