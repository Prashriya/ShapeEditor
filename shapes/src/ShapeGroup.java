/**
 * this class represents group of shape and different functionalities
 */
import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeGroup extends MyShape{

	private ArrayList<MyShape> group;
	private double ulx, uly;

	public ShapeGroup() {
		group = new ArrayList<>();
	}

	public ArrayList<MyShape> getMembers(){
		return group;
	}
	/**
	 * returns true if the group is empty otherwise returns false
	 * @return
	 */
	public boolean isEmpty() {
		return group.isEmpty();
	}

	/**
	 * returns the number of shape in the group
	 * @return
	 */
	public int size() {
		return group.size();
	}

	/**
	 * creates deep copy of ShapeGroup object
	 */
	@Override
	public Object clone() {
		ShapeGroup cloneGroup = new ShapeGroup();

		for(MyShape s: group) {

			cloneGroup.addMember((MyShape) s.clone());
		}

		updateCenter();
		updateBounds();

		return cloneGroup;
	}

	/**
	 * adds shape to the group if it is not present
	 * @param shape
	 */
	public void addMember(MyShape shape) {

		if(!group.contains(shape)) {

			group.add(shape);
			updateCenter();
		}
	}

	/**
	 * removes shape from the group
	 * @param shape
	 */
	public void removeMember(MyShape shape) {
		group.remove(shape);

		updateCenter();
	}

	/**
	 * checks if the shape falls within the bounding box of the group
	 * @param shape
	 * @return
	 */
	public boolean within(MyShape shape) {
		Point2D shapeCenter = shape.getCenter();

		if(shapeCenter.getX() > ulx && shapeCenter.getX() < (ulx + width) && shapeCenter.getY() > uly && shapeCenter.getY() < (uly + height)) {
			return true;
		}
		else {
			return false;
		}	
	}

	/**
	 * updates the center of the group based on center of all shapes in the group
	 */
	@Override
	public void updateCenter() {
		double sumX = 0;
		double sumY = 0;

		double avgX = 0;
		double avgY = 0;

		for(MyShape s : group) {
			sumX += s.getCenter().getX();
			sumY += s.getCenter().getY();
		}

		avgX = sumX / group.size();
		avgY = sumY/ group.size();

		center = new Point2D(avgX, avgY);
	}

	/**
	 * moves all shapes in the group
	 */
	@Override
	public void move(double dx, double dy) {

		for(MyShape s : group) {
			s.move(dx, dy);
		}

		super.move(dx, dy);

		updateBounds();
		updateCenter();
	}

	@Override
	public void draw(GraphicsContext gc) {
		
		for(MyShape s : group) {
			s.draw(gc);
			s.drawBounds(gc);
		}
		
		gc.setStroke(Color.LIGHTGRAY);
		drawBounds(gc);

	}

	@Override
	public String toString() {
		
		String str = String.format("ShapeGroup %d\n", group.size(), ulx, uly); 
		
		for (MyShape shape : group) {
			str += shape.toString() + "\n";
		}
		
		return str;
	}	
}


