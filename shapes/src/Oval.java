import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Oval extends MyShape implements Serializable{
	/*
	 * Default constructor that calls the parent constructor
	 */
	public Oval() {
		super();
	}
	
	/*
	 * Constructor that takes two Point2D obj p1 and p2 as parameters
	 */
	public Oval(Point2D p1, Point2D p2) {
		super(p1, p2);
	}
	
	public Oval(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
	/*
	 * Draws oval on the canvas
	 */
	public void draw(GraphicsContext gc) {
		//draws the bounding rectangle
		drawBounds(gc);
		gc.setStroke(color);        //Setting the stroke color
		
		
		//if the shape is filled then draws the filled oval
		if(filled) {
			gc.setFill(color);
		    gc.fillOval(ulx, uly, width, height);
		}
			
		//if the shape is not filled then draws the stroked oval
		else {
			gc.strokeOval(ulx, uly, width, height);
		}
	}
	
	@Override
	public String toString() {
		return "Oval " + super.toString();
	}
}