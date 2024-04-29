import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Line extends MyShape implements Serializable{
	//Default constructor
	public Line() {
		super();
	}
	
	//Constructor with 2 Point2D parameters
	public Line(Point2D p1, Point2D p2) {
		super(p1, p2);
	}
	
	//Constructor with four double parameters(x1, y1, x2, y2)
	public Line(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
	//Draws line from p1 to p2
	@Override
	public void draw(GraphicsContext gc) {
	    gc.setStroke(color);

		drawBounds(gc);
		
	    gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
	
	@Override
	public String toString() {
		return "Line " + super.toString();
	}
}