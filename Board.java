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
	private BoardPoint[][] board;
	private ArrayList<Point> theClicked;
	private boolean[] squaresMade;
	private int p1Score;
	private int p2Score;
	private int rLoc;
	private int cLoc;
	private int rLoc2;
	private int cLoc2;
	private boolean p1Turn;
	@Override
	public void start(Stage stage) {
		stage.setTitle("Dots and Boxes v 0.2.0"); //Stage contains everything
		Group root = new Group();
		stage.setWidth(375);
		stage.setHeight(375);
		squaresMade = new boolean[9];
		for(int i = 0; i < squaresMade.length; i++)
		{
			squaresMade[i] = false;
		}
		board = new BoardPoint[4][4];
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
				board[r][c] = new BoardPoint(x,y,5);
				board[r][c].boardCircle().setStroke(Color.BLACK);
				root.getChildren().add(board[r][c].boardCircle());
				c++;
			}
			r++;
			c = 0;
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
							if(board[r][c].boardCircle().contains(e.getX(), e.getY()))
							{
								theClicked.add(new Point(board[r][c].boardCircle().getCenterX(), board[r][c].boardCircle().getCenterY()));
								if(theClicked.size() == 1)
								{
									rLoc = r;
									cLoc = c;
								}
								else if(theClicked.size() == 2)
								{
									rLoc2 = r;
									cLoc2 = c;
								}
							}
						}
					}
					setDirections();
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
		else if(theClicked.size() == 2 && (theClicked.get(0).getX() == theClicked.get(1).getX() && theClicked.get(0).getY() == theClicked.get(1).getY()))
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
			board[rLoc][cLoc].setDirectionTrue(2);
			board[rLoc2][cLoc2].setDirectionTrue(4);
		}
		else if(theClicked.get(0).getX() > theClicked.get(1).getX())
		{
			board[rLoc][cLoc].setDirectionTrue(4);
			board[rLoc2][cLoc2].setDirectionTrue(2);
		}
		else if(theClicked.get(0).getY() < theClicked.get(1).getY())
		{
			board[rLoc][cLoc].setDirectionTrue(3);
			board[rLoc2][cLoc2].setDirectionTrue(1);
		}
		else if(theClicked.get(0).getY() > theClicked.get(1).getY())
		{
			board[rLoc][cLoc].setDirectionTrue(1);
			board[rLoc2][cLoc2].setDirectionTrue(3);
	    }
	}
	
	public void checkSquare()
	{
		int curCheck = 0;
		for(int r = 0; r < board.length - 1; r++)
		{
			for(int c = 0; c < board[0].length - 1; c++)
			{
				if(board[r][c].retDir(2) && board[r][c].retDir(3) && board[r+1][c].retDir(2))
				{
					squaresMade[curCheck] = true;
				}
				curCheck++;
			}
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
		public boolean retDir(int d) //Return direction value
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

	public class BoardPoint extends Point //A point AND a circle
	{	
		private Circle c;
		public BoardPoint(double x, double y, int r)
		{
			super(x,y);
			c = new Circle(x,y,r);
		}
		public Circle boardCircle()
		{
			return c;
		}
	}

	public class Box //Boxes
	{
		private boolean green; //track of which player made this box
		public Box(boolean isPlayer1)
		{
			green = isPlayer1;
		}
	}
}
