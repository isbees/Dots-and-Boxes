package application;
	
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;


public class Board extends Application {
	@Override
	public void start(Stage stage) {
		stage.setTitle("Dots And Boxes v 0.0.1 Beta"); //Stage contains everything
		Group root = new Group();
		int rows = 4 * 100;
		int columns = 4 * 100;
		for(int x = 10; x < rows; x+=100)
		{
			for(int y = 10; y < columns; y+= 100)
			{
				Circle c = new Circle(x,y,5);
				c.setStroke(Color.BLACK);
				root.getChildren().add(c);
			}
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args); //Launches the start method above
	}
}
