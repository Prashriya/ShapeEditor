/**
 * this class represents a shape that has two points p1 and p2
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class MyShape implements Serializable, Cloneable{
	protected transient Point2D p1, p2, center;
	protected transient Color color;
	protected boolean filled;
	protected double ulx, uly, width, height;

	/**
	 * Default constructor
	 */
	public MyShape() {
		p1 = new Point2D(0, 0);
		p2 = new Point2D(0, 0);
		
		color = Color.BLACK;
		filled = false;
	}

	/*
	 * Constructor that takes two Point2D parameters
	 */
	public MyShape(Point2D p1, Point2D p2) {
		this.p1 = p1;
		this.p2 = p2;
		color = Color.BLACK;
		filled = false;

		updateBounds();
		updateCenter();

	}

	/**
	 * Constructor that takes four parameters (x1, y1, x2, y2)
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public MyShape(double x1, double y1, double x2, double y2) {
		this(new Point2D(x1, y1), new Point2D(x2, y2));
	}

	//Getter and setter methods to retrieve and set the values

	public Point2D getP1() {
		return p1;
	}

	public Point2D getP2() {
		return p2;
	}

	public Color getColor() {
		return color;
	}

	public boolean isFilled() {
		return filled;
	}

	public double getULX() {
		return ulx;
	}

	public double getULY() {
		return uly;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public Point2D getCenter() {
		return center;
	}

	public void setP1(Point2D p1) {
		this.p1 = p1;

	}

	public void setP1(double x, double y) {
		p1 = new Point2D(x, y);

	}

	public void setP2(Point2D p2) {
		this.p2 = p2;

		updateBounds();
		updateCenter();

	}

	public void setP2(double x, double y) {

		p2 = new Point2D(x, y);

		updateBounds();
		updateCenter();

	}
	public void setColor(Color color) {
		this.color = color;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	/**
	 * Calculating ulx, uly, width, height for the bounding rectangle from p1 and p2
	 */
	public void updateBounds() {

		ulx = Math.min(p1.getX(), p2.getX());
		uly = Math.min(p1.getY(), p2.getY());

		width = Math.abs(p2.getX() - p1.getX());
		height = Math.abs(p2.getY() - p1.getY());

	}

	/**
	 * Method to update the center of the shape
	 */
	public void updateCenter() {

		center = p1.midpoint(p2);

	}

	/**
	 * Method to calculate distance between center and the given point (x,y)
	 * @param x
	 * @param y
	 * @return
	 */
	public double distance(double x, double y) {
		return center.distance(x, y);
	}

	/**
	 * Drawing dashed bounding rectangle around the shape
	 * @param gc
	 */
	public void drawBounds(GraphicsContext gc) {
		gc.setLineDashes(6);
		gc.strokeRect(ulx, uly, width, height);
		gc.setLineDashes(null);	

	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		//default serialization
		out.defaultWriteObject();

		//handle color
		out.writeDouble(color.getRed());
		out.writeDouble(color.getGreen());
		out.writeDouble(color.getBlue());

		out.writeDouble(p1.getX());
		out.writeDouble(p1.getY());
		out.writeDouble(p2.getX());
		out.writeDouble(p2.getY());

	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();

		//handle color
		double red = in.readDouble();
		double green = in.readDouble();
		double blue = in.readDouble();

		//update color
		color = Color.color(red, green, blue);

		double x1 = in.readDouble();
		double y1 = in.readDouble();
		double x2 = in.readDouble();
		double y2 = in.readDouble();

		p1 = new Point2D(x1, y1);
		p2 = new Point2D(x2, y2);

	}

	public void move(double dx, double dy) {

		setP1(p1.getX() + dx, p1.getY() + dy);
		setP2(p2.getX() + dx, p2.getY() + dy);


		updateCenter();
		updateBounds();
	}

	//change this code
	@Override
	public Object clone(){
		try {
			MyShape cloneShape = (MyShape) super.clone();

			cloneShape.p1 = new Point2D(p1.getX(), p1.getY());
			cloneShape.p2 = new Point2D(p2.getX(), p2.getY());

			cloneShape.color = color;
			cloneShape.filled = filled;

			cloneShape.updateCenter();
			cloneShape.updateBounds();

			return cloneShape;
		} 
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public String toString() {
		return String.format("%.0f %.0f %.0f %.0f %.3f %.3f %.3f %b", p1.getX(), p1.getY(), p2.getX(), p2.getY(), color.getRed(), color.getGreen(), color.getBlue(), isFilled());
	}

	/**
	 * Method to draw the shape
	 * @param gc
	 */
	public abstract void draw(GraphicsContext gc);

}