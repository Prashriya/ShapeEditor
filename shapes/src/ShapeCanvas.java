/**
 * This class draws and manages shapes in canvas
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeCanvas extends Canvas {
	public static final String FILENAME = "drawing.txt";
	private GraphicsContext gc;
	private ArrayList<MyShape> shapes;
	private MyShape currShape;
	private transient Color currColor;
	private boolean currFilled;
	private Stack<Edit> stackUndo;
	private Stack<Edit> stackRedo;

	/**
	 * Constructor that creates a canvas of the given width and height
	 * @param width
	 * @param height
	 */
	public ShapeCanvas(double width, double height) {

		super(width, height);

		gc = getGraphicsContext2D();
		shapes = new ArrayList<>();
		currShape = null;
		currColor = Color.BLACK;
		currFilled = false;
		
		stackUndo = new Stack<>();
		stackRedo = new Stack<>();
	}


	public MyShape getCurrShape() {
		return currShape;
	}

	public void setFilled(boolean filled) {
		this.currFilled = filled;
	}

	public void setCurrColor(Color color) {
		currColor = color;
	}

	public boolean isCurrFilled() {
		return currFilled;
	}

	public Color getCurrColor() {
		return currColor;
	}

	/**
	 * Clears the canvas
	 */
	public void clearCanvas() {

		shapes.clear();
		paint();
	}

	/**
	 * Method that repaints the canvas
	 */
	public void paint() {

		gc.clearRect(0, 0, getWidth(), getHeight());    //Clears the canvas

		for (MyShape s : shapes) {
			s.draw(gc);

		}
		if (currShape != null) {     //Draws currShape if it exists
			currShape.draw(gc);

		}
	}

	/**
	 * Method that adds shapes to the list
	 * @param s
	 */
	public void addShape(MyShape s) {

		shapes.add(s);
	}

	/**
	 * Method that sets current shape to the given shape and updates its properties
	 * @param s
	 */
	public void setCurrentShape(MyShape s) {

		currShape = s;

		if (s != null) {
			s.color = currColor;
			s.filled = currFilled;
		}
	}

	public ArrayList<MyShape> getShape() {
		return shapes;
	}

	public void clear() {

		shapes.clear();
		currShape = null;
		clearCanvas();
	}

	/**
	 * replaces the current mouse listener (press/release) and mouse motion listener (drag)
	 * with the passed listener object
	 *
	 * @param listener an EventHandler object
	 */
	public void replaceMouseHandler(EventHandler listener) {

		setOnMousePressed(listener);
		setOnMouseDragged(listener);
		setOnMouseReleased(listener);
		setOnMouseClicked(listener);
	}

	/**
	 * Saves the list of drawn shapes to a text file
	 * @param fileObj
	 */
	public void toTextFile(File fileObj) {
		try {
			
			PrintWriter fileOut = new PrintWriter(fileObj);

			fileOut.println(shapes.size());


			for (MyShape s : shapes) {

				fileOut.println(s.toString());
			}

			fileOut.close();
		}
		catch(FileNotFoundException e) {
			System.out.println(FILENAME + " could not be opened/created for writing");
			e.printStackTrace();
		}
	}

	/**
	 * Reads the text file and loads them into canvas
	 * @param fileObj
	 */
	public void fromTextFile(File fileObj) {
		try {
			
			Scanner fileIn = new Scanner(fileObj);

			clearCanvas();

			int nShapes = fileIn.nextInt();

			for(int i = 0; i < nShapes; i++) {

				String type = fileIn.next();

				MyShape shape = null;

				if(type.equalsIgnoreCase("ShapeGroup")) {

					shape = loadGroupText(fileIn);
				}
				else {
					shape = loadSingletonText(fileIn, type);
				}

				shapes.add(shape);

			}

			fileIn.close();
			paint();
		}
		catch(FileNotFoundException e) {
			System.out.println(FILENAME + "Could not be opened/created for reading");
			e.printStackTrace();
		}
	}

	/**
	 * Saves the list of drawn shapes to a binary file
	 * @param fileObj
	 */
	public void toBinaryFile(File fileObj) {
		try {
			FileOutputStream fOS = new FileOutputStream(fileObj);
			ObjectOutputStream fOut = new ObjectOutputStream(fOS);

			fOut.writeInt(shapes.size());

			for(MyShape shape : shapes) {
				fOut.writeObject(shape);
			}

			fOut.close();
			fOS.close();
		}
		catch(FileNotFoundException e) {
			System.out.println(FILENAME + " Could not be opened/created for reading");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println(FILENAME + " IO Exception");
			e.printStackTrace();
		}
	}

	/**
	 * Reads the binary file and loads them into canvas
	 * @param fileObj
	 */
	public void fromBinaryFile(File fileObj) {
		try {
			FileInputStream fIS = new FileInputStream(fileObj);
			ObjectInputStream fIn = new ObjectInputStream(fIS);

			int n = fIn.readInt();

			clearCanvas();	

			for(int i = 0; i < n; i++) {
				shapes.add((MyShape) fIn.readObject());
			}

			fIn.close();
			fIS.close();

			paint();
		}
		
		catch(FileNotFoundException e) {
			System.out.println(FILENAME + "Could not be opened/created for reading");
			e.printStackTrace();
		}
		catch(IOException e) {
			System.out.println("IO Exception");
		}
		catch(ClassNotFoundException e) {
			System.out.println(FILENAME + "CLass could not be found");
			e.printStackTrace();
		}
	}

	public MyShape closestShape(double x, double y) {
		
		if(shapes.isEmpty()) {
			return null;
		}

		MyShape closeShape = shapes.get(0);
		double minDistance = closeShape.distance(x, y);

		for(MyShape s : shapes) {

			double distance = s.distance(x, y);

			if(distance < minDistance) {

				minDistance = distance;
				closeShape = s;
			}

		}

		return closeShape;
	}

	public void deleteShape(MyShape s) {
		shapes.remove(s);
		paint();
		
	}

	private MyShape loadSingletonText(Scanner fIn, String shapeType) {

		String type = shapeType;
		
		int x1 = fIn.nextInt();
		int y1 = fIn.nextInt();

		int x2 = fIn.nextInt();
		int y2 = fIn.nextInt();

		double r = fIn.nextDouble();
		double g = fIn.nextDouble();
		double b = fIn.nextDouble();

		boolean isFilled = fIn.nextBoolean();

		Color col = Color.color(r, g, b);


		MyShape shape = null;
		if(type.equalsIgnoreCase("Line")) {
			
			shape = new Line(x1, y1, x2, y2);
		}
		else if(type.equalsIgnoreCase("Rect")){
			
			shape = new Rect(x1, y1, x2, y2);
		}
		else {
			
			shape = new Oval(x1, y1, x2, y2);
		}
		

		shape.setColor(col);
		shape.setFilled(isFilled);

		return shape;

	}

	private ShapeGroup loadGroupText(Scanner fIn) {
		
		ShapeGroup group =  new ShapeGroup();
		int n = fIn.nextInt();

		for (int i = 0; i < n; i++) {
			
			String type = fIn.next();
			
			if (type.equalsIgnoreCase("ShapeGroup")) {

				group.addMember(loadGroupText(fIn));
			} 
			else {
				group.addMember(loadSingletonText(fIn, type));
			}
		}

		return group;
	}
	
	public void addEdit(Edit edit) {
		
		stackUndo.push(edit);

	}
	
	public void undo() {
		
		if(stackUndo.isEmpty() != true) {
			
			Edit undoEdit = stackUndo.pop();
			undoEdit.undo();

			stackRedo.push(undoEdit);
			
		}
	}
	
	public void redo() {
	    
		if(stackRedo.isEmpty() != true) {
			
			Edit redoEdit = stackRedo.pop();
			redoEdit.redo();
			
			stackUndo.push(redoEdit);
		}
	}

}