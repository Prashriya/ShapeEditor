import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Rect extends MyShape implements Serializable{
	
	/**
	 * Default constructor
	 */
	public Rect() {
		super();
	}
	
	/**
	 * Constructor that takes two Point2D parameters
	 * @param p1
	 * @param p2
	 */
	public Rect(Point2D p1, Point2D p2) {
		super(p1, p2);
	}
	
	/**
	 * Constructor that takes four double parameters
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public Rect(double x1, double y1, double x2, double y2) {
		super(x1, y1, x2, y2);
	}
	
	/**
	 * Drawing the rectangle
	 */
	@Override
	public void draw(GraphicsContext gc) {
		
		drawBounds(gc);          //Drawing the bouding rectangle
	    gc.setStroke(color);
	    
	    //if filled is true then draws the filled rectangle
	    if (filled) {
	        gc.setFill(color);
	        gc.fillRect(ulx, uly, width, height);
	        
	    } 
	    //if the filled is false then drawing the stroked rectangle
	    else {
	        gc.strokeRect(ulx, uly, width, height);
	    }
	}
	
	@Override
	public String toString() {
		return "Rect " + super.toString();
	}
}