/**
 * @author prashriyaacharya
 */

import java.io.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ShapeEditor extends Application{
	//constants
	private static final int APP_WIDTH = 800;
	private static final int APP_HEIGHT = 700;
	private static final int CANVAS_WIDTH = 800;
	private static final int CANVAS_HEIGHT = 700;
	
	private BorderPane mainPane;
	private ShapeCanvas canvas;
	private HBox controlPanel;
	private Button bnClear;
	private CheckBox cbFilled;
	private RadioButton rbLine, rbOval, rbRect, rbDelete, rbMove, rbCopy, rbGroup;
	private LineHandler lineHandler;
	private OvalHandler ovalHandler;
	private RectHandler rectHandler;
	private DeleteHandler deleteHandler;
	private MoveHandler moveHandler;
	private CopyHandler copyHandler;
	private GroupHandler groupHandler;
	private ColorPicker colorPicker;
	private Button undo, redo;
	
	private FileChooser      fcOpenSave;

    private MenuBar          menuBar;
    private Menu             menuFile, menuAbout;
    private MenuItem         miLoad, miSave;
    private MenuItem         miLoadB, miSaveB;

	
	private Color curColor = Color.BLACK;
	
	@Override
	public void start(Stage stage) {
		//Setting up the main layout pane
		mainPane = new BorderPane();
		
		setupCanvas();
		setupMenu();
		setupControls();
		
		//Adding the canvas to the center
        mainPane.setCenter(canvas);

        //Creating and setting up the scene
		Scene scene = new Scene(mainPane, APP_WIDTH, APP_HEIGHT);
		
		stage.setScene(scene);
		stage.setTitle("PaintApp");
		
		stage.show();
		
	}
	
	/**
	 * Setting up the canvas
	 */
	private void setupCanvas() {

		canvas = new ShapeCanvas(CANVAS_WIDTH, CANVAS_HEIGHT);
	}
	
	//Setting up the control panel
	private void setupControls() {
		controlPanel = new HBox();
		
		//Creating radio buttons for shape selection
		rbLine = new RadioButton("Line");
		rbRect = new RadioButton("Rectangle");
		rbOval = new RadioButton("Oval");
		rbDelete = new RadioButton("Delete");
		rbMove = new RadioButton("Move");
		rbCopy = new RadioButton("Copy");
		rbGroup = new RadioButton("Group");
		
		rbLine.setSelected(true);
		
		//Grouping the buttons together
		ToggleGroup group = new ToggleGroup();
		rbLine.setToggleGroup(group);
		rbRect.setToggleGroup(group);
		rbOval.setToggleGroup(group);
		rbDelete.setToggleGroup(group);
		rbMove.setToggleGroup(group);
		rbCopy.setToggleGroup(group);
		rbGroup.setToggleGroup(group);
		
		//Creating CheckBox for filled option
		cbFilled = new CheckBox("Filled");
		
		//Creating clear canvas button
		bnClear = new Button("Clear Canvas");
		undo = new Button("Undo");
		redo = new Button("Redo");
		
		//Event handler for fill checkbox
		cbFilled.setOnAction(e -> {
	        canvas.setFilled(cbFilled.isSelected());
	    });
		
		//Creating handlers to draw shape
		lineHandler = new LineHandler(canvas);
	    canvas.replaceMouseHandler(lineHandler);      //Setting the inital handler for line drawing
        ovalHandler = new OvalHandler(canvas);
        rectHandler = new RectHandler(canvas);
        deleteHandler = new DeleteHandler(canvas);
        moveHandler = new MoveHandler(canvas);
        copyHandler = new CopyHandler(canvas);
        groupHandler = new GroupHandler(canvas);
        
		
        //Setting event handlers for shape selection radio buttons
		rbLine.setOnAction(e -> {

			canvas.replaceMouseHandler(lineHandler);
		});
		
		rbRect.setOnAction(e -> {
			canvas.replaceMouseHandler(rectHandler);
		});
		
		rbOval.setOnAction(e -> {
			canvas.replaceMouseHandler(ovalHandler);
		});
	
		rbDelete.setOnAction(e -> {
			canvas.replaceMouseHandler(deleteHandler);
		});
		
		rbMove.setOnAction(e -> {
			canvas.replaceMouseHandler(moveHandler);
		});
		
		rbCopy.setOnAction(e -> {
			canvas.replaceMouseHandler(copyHandler);
		});
		
		rbGroup.setOnAction(e -> {
			canvas.replaceMouseHandler(groupHandler);
		});
		
		//Setting event handlers for clear canvas button
		bnClear.setOnAction(e -> {
			canvas.clear();
		});		
		
		undo.setOnAction(e -> {
			canvas.undo();
		});
		
		redo.setOnAction(e -> {
			canvas.redo();
		});
		
		colorPicker = new ColorPicker(curColor);
		colorPicker.setOnAction(e -> {
			curColor = colorPicker.getValue();
			canvas.setCurrColor(curColor);
		});
		
		//Adding them to control panel
		controlPanel.getChildren().addAll(rbLine, rbRect, rbOval, cbFilled, rbDelete,rbMove, rbCopy, rbGroup, colorPicker, bnClear,  undo, redo);
		
		//Adding control panel to the top of mainPane
		mainPane.setTop(controlPanel);
	}
	
	public void setupMenu() {
		menuBar = new MenuBar();

        menuFile = new Menu("File");
        menuAbout = new Menu("About");

        miLoad    = new MenuItem("Load");
        miSave    = new MenuItem("Save");

        miLoadB   = new MenuItem("Load Binary");
        miSaveB   = new MenuItem("Save Binary");

        fcOpenSave = new FileChooser();
        
     // event handlers for menu items
        miSave.setOnAction(e -> {
            fcOpenSave.setTitle("Save current drawing as");

            File newFile = fcOpenSave.showSaveDialog(null);

            if (newFile != null) {
                canvas.toTextFile(newFile);
            }
        });

        miLoad.setOnAction(e -> {
            fcOpenSave.setTitle("Load a drawing from");

            File newFile = fcOpenSave.showOpenDialog(null);

            if (newFile != null) {
                canvas.fromTextFile(newFile);
            }
        });

        // event handlers for menu items
        miSaveB.setOnAction(e -> {
            fcOpenSave.setTitle("Save current drawing as");

            File newFile = fcOpenSave.showSaveDialog(null);

            if (newFile != null) {
                canvas.toBinaryFile(newFile);
            }
        });

        miLoadB.setOnAction(e -> {
            fcOpenSave.setTitle("Load a drawing from");

            File newFile = fcOpenSave.showOpenDialog(null);

            if (newFile != null) {
                canvas.fromBinaryFile(newFile);
            }
        });

        menuBar.getMenus().addAll(menuFile, menuAbout);

        menuFile.getItems().addAll(miLoad, miSave, miLoadB, miSaveB);

        mainPane.setLeft(menuBar);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}