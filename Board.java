package application;

import javafx.application.*;
import javafx.event.EventHandler.*;
import javafx.event.*;
import java.util.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class Board extends Application {
	private Circle[][] board;		// contains all dots
	private ArrayList<Point> theClicked;
	private int p1Score;
	private int p2Score;
	private boolean p1Turn;
	@Override
	public void start(Stage stage) {
		stage.setTitle("The Appatar v 0.1.5"); //Stage contains everything
		Group root = new Group();
		stage.setWidth(375);
		stage.setHeight(375);
		board = new Circle[4][4]; //array 4by4
		p1Turn = true;
		theClicked = new ArrayList<Point>();
		int rows = 4 * 100;
		int columns = 4 * 100;
		int r = 0;
		int c = 0;
		for(int x = 10; x < rows; x+=100)
		{
			for(int y = 10; y < columns; y+= 100)
			{
				Circle ci = new Circle(x,y,5);
				board[r][c] = ci;		//adds the dots into the board
				c++;				//left to right
				ci.setStroke(Color.BLACK);
				root.getChildren().add(ci);
			}
			r++; 					//down
			c = 0;					//reset
		}
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.setOnMouseClicked
		( 
			new EventHandler<MouseEvent>()
			{
				public void handle(MouseEvent e)
				{
					for(int r = 0; r < board.length; r++)
					{
						for(int c = 0; c < board[0].length; c++)
						{
							if(board[r][c].contains(e.getX(), e.getY()))
							{
								theClicked.add(new Point(board[r][c].getCenterX(), board[r][c].getCenterY()));
							}
						}
					}
					Line ln = checkIfPaired();
					if(ln.getStartX() != 0)
					{
						root.getChildren().add(ln);
					}
				}
			}
		);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args); //Launches the start method above
	}
	
	public Line checkIfPaired() //Sees if two clicked spots are dots and if vertical or horizontal, draws a line
	{
		Line ln;
		if(theClicked.size() == 2 && (theClicked.get(0).getX() < theClicked.get(1).getX() && theClicked.get(0).getY() < theClicked.get(1).getY()))
		{
			theClicked.remove(0);
			theClicked.remove(0);
			ln = new Line();
		}
		else if(theClicked.size() == 2 && (theClicked.get(0).getX() > theClicked.get(1).getX() && theClicked.get(0).getY() > theClicked.get(1).getY()))
		{
			theClicked.remove(0);
			theClicked.remove(0);
			ln = new Line();
		}
		else if(theClicked.size() == 2 && (theClicked.get(0).getX() > theClicked.get(1).getX() && theClicked.get(0).getY() < theClicked.get(1).getY()))
		{
			theClicked.remove(0);
			theClicked.remove(0);
			ln = new Line();
		}
		else if(theClicked.size() == 2 && (theClicked.get(0).getX() < theClicked.get(1).getX() && theClicked.get(0).getY() > theClicked.get(1).getY()))
		{
			theClicked.remove(0);
			theClicked.remove(0);
			ln = new Line();
		}
		if(theClicked.size() == 2)
		{
			int x1 = (int) theClicked.get(0).getX();
			int y1 = (int) theClicked.get(0).getY();
			int x2 = (int) theClicked.get(1).getX();
			int y2 = (int) theClicked.get(1).getY();
			ln = new Line(x1,y1,x2,y2);
			if(p1Turn == true)
			{
				ln.setStroke(Color.GREEN);
				p1Turn = false;
			}
			else if(p1Turn == false)
			{
				ln.setStroke(Color.RED);
				p1Turn = true;
			}
			theClicked.remove(0);
			theClicked.remove(0);
		}
		else
		{
			ln = new Line();
		}
		return ln;
	}
	
	public void setDirections() //Once a line is, drawn this sets the corresponding directions to true
	{
		if(theClicked.get(0).getX() < theClicked.get(1).getX())
		{
			theClicked.get(0).setDirectionTrue(2);
			theClicked.get(1).setDirectionTrue(4);
		}
		else if(theClicked.get(0).getX() > theClicked.get(1).getX())
		{
			theClicked.get(0).setDirectionTrue(4);
			theClicked.get(1).setDirectionTrue(2);
		}
		else if(theClicked.get(0).getY() < theClicked.get(1).getY())
		{
			theClicked.get(0).setDirectionTrue(3);
			theClicked.get(1).setDirectionTrue(1);
		}
		else if(theClicked.get(0).getY() > theClicked.get(1).getY())
		{
			theClicked.get(0).setDirectionTrue(1);
			theClicked.get(1).setDirectionTrue(3);
	}
}

class Point //All of the clickable circles are points
{
	private double x;
	private double y;
	private boolean d1;
	private boolean d2;
	private boolean d3;
	private boolean d4;
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
		d1 = false;
		d2 = false;
		d3 = false;
		d4 = false;
	}
	public double getX(){return x;}
	public double getY(){return y;}
	public boolean equals(Point otherPoint) //Checks if two points are in the same place
	{
		if(this.getX() == otherPoint.getX() && this.getY() == otherPoint.getY())
		{
			return true;
		}
		return false;
	}
	public void setDirectionTrue(int d) //Make a direction true
	{
		if(d == 1)
		{
			d1 = true;
		}
		else if(d == 2)
		{
			d2 = true;
		}
		else if(d == 3)
		{
			d3 = true;
		}
		else if(d == 4)
		{
			d4 = true; 
		}
	}
		public boolean returnDirection(int d) //Return direction value
	{
		if(d == 1)
		{
			return d1;
		}
		else if(d == 2)
		{
			return d2;
		}
		else if(d == 3)
		{
			return d3;
		}
		else if(d == 4)
		{
			return d4; 
		}
		else
		{
			return false;
		}
	}
}
}
