import javax.swing.* ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.geom.Ellipse2D ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseMotionListener ;
import java.util.Random;
import java.awt.Rectangle ;
import java.awt.Color ;
import java.awt.geom.Arc2D ;
import java.awt.BasicStroke ;

/**
 * @author Alex Wazonek
 * Creates a slow moving prey that looks like an insect
 */
public class SlowPrey extends Creature
{
	Random random = new Random();
	int newDir;
	final int SPEED = 1;
	final int UP = 0;
	final int RIGHT = 1;
	final int DOWN = 2;
	final int LEFT = 3;
	boolean outOfBounds = false;
	final static BasicStroke thinStroke = new BasicStroke(1.0f) ;
	Rectangle range;
	boolean running = false;
	final int MAX_MOVEMENT = 8; //used to determine how often the prey will change direction
	final int RANGE_SIZE = 100;
	final int MAX_DIRECTIONS = 4;
	final int BOUNDARY_RANGE = 30;
	final int EDGE_OF_FIELD = 0;
	final int LEFT_LEG_START = 90;
	final int RIGHT_LEG_START = 0;
	final int LEG_ANGLE = 100;
	int typeOfMovement;
	final int MOVEMENT_TYPE_1 = 0;
	final int MOVEMENT_TYPE_2 = 1;
	final int MOVEMENT_TYPE_TOTAL = 2;
	
	/**
	 * Creates the predator, calls the super class and constructs that. 
	 * Determines the "type of movement" that the prey will move in. 0 is randomly, and 1 is in a small area side to side or up and down. 
	 * @param x the X location on the playing field
	 * @param y the Y location on the playing field
	 * @param w the width of the creature, used for implementing the bounding box
	 * @param h the height of the creature
	 * @param speed how fast the creature is
	 * @param dir the direction the creature is facing
	 * @param framew the width of the playing field, used to determing where the prey and predator cannot go. 
	 * @param frameh the height of the playing field
	 */
	public SlowPrey(int x, int y, int w, int h, int speed, int dir,int framew, int frameh)
	{
		super(x,y,w,h,speed,dir,framew,frameh);
		typeOfMovement = random.nextInt(MOVEMENT_TYPE_TOTAL);
	}
	
	/**
	 * Moves the prey by pixels equal to SPEED (1 in this case), or SPEED + 1 if its running away from the predator
	 */
	public void move()
	{
		
		if(getDirection() == UP)
		{
			y-= SPEED;
			if(running)
				y--;
		}
		else if(getDirection() == RIGHT)
		{
			x+= SPEED;
			if(running)
				x++;
		}
		else if(getDirection() == DOWN)
		{
			y+= SPEED;
			if(running)
				y++;
		}
		else if(getDirection() == LEFT)
		{
			x-= SPEED;
			if(running)
				x--;
		}
		boundingBox = new Rectangle(getX(),getY(),getWidth(),getHeight());
		if(!running)
			newDirection();
		checkBounds();
		
	}
	
	/**
	 * The slow prey are given a different movement pattern based on what their "typeOfMovement" is equal to.
	 * If the prey is touching the edge of the map it will not give it a random direction, instead it will keep moving in the direction it is in until it is within the BOUNDARY_RANGE again. 
	 * If the prey have movement type 0, they move randomly in a small area. 
	 * If the prey have movement type 1, they move either up and down or side to side 
	 */
	public void newDirection()
	{
		if(!outOfBounds)
			{
			if(typeOfMovement == MOVEMENT_TYPE_1)
			{
				newDir = random.nextInt(MAX_MOVEMENT);
				if(newDir == getDirection())
					setDirection(random.nextInt(MAX_DIRECTIONS));
			}
			else if(typeOfMovement == MOVEMENT_TYPE_2)
				newDir = random.nextInt(MAX_MOVEMENT);
				if(newDir == getDirection())
				{
					if(getDirection() == LEFT)
						setDirection(RIGHT);
					else if(getDirection() == RIGHT)
						setDirection(LEFT);
					else if(getDirection() == UP)
						setDirection(DOWN);
					else if(getDirection() == DOWN)
						setDirection(UP);
				}
			}
		else if(((y > BOUNDARY_RANGE) && (x > BOUNDARY_RANGE)) || ((x < getFrameWidth() - BOUNDARY_RANGE) && (y < getFrameHeight() - BOUNDARY_RANGE)))
				outOfBounds = false;
	}
	
	/**
	 * Checks to see if the prey is touching the bounds of the playing field, and turns it around if it is. 
	 */
	public void checkBounds()
	{
		if(y <= EDGE_OF_FIELD)
		{
			setDirection(DOWN);
			y+= SPEED;
			outOfBounds = true;
		}
		if(y >= getFrameHeight() - getHeight())
		{
			setDirection(UP);
			y-= SPEED;
			outOfBounds = true;
		}
		if(x <= EDGE_OF_FIELD)
		{
			setDirection(RIGHT);
			x+= SPEED;
			outOfBounds = true;
		}
		if(x >= getFrameWidth() - getWidth())
		{
			setDirection(LEFT);
			x-= SPEED;
			outOfBounds = true;
		}
	}
	
	/**
	 * Draws the Slow Prey, which is shaped like a small red bug. 
	 * @param g2 the graphics context
	 */
	public void draw(Graphics2D g2)
	{
	Ellipse2D.Double oval = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight()) ;
	Arc2D.Double leg1 = new Arc2D.Double(getX() -getHeight()/8,getY() + (getHeight()/4),getWidth()/2,getHeight()/2,LEFT_LEG_START,LEG_ANGLE,Arc2D.OPEN);
	Arc2D.Double leg2 = new Arc2D.Double(getX() - getHeight()/8,getY() + (getHeight()/2),getWidth()/2,getHeight()/2,LEFT_LEG_START,LEG_ANGLE,Arc2D.OPEN);
	Arc2D.Double leg3 = new Arc2D.Double(getX() - getHeight()/8,getY() + (getHeight() - getHeight()/4),getWidth()/2,getHeight()/2,LEFT_LEG_START,LEG_ANGLE,Arc2D.OPEN);
	Arc2D.Double leg4 = new Arc2D.Double(getX() + (getHeight()/1.5),getY() + (getHeight()/4),getWidth()/2,getHeight()/2,RIGHT_LEG_START,LEG_ANGLE,Arc2D.OPEN);
	Arc2D.Double leg5 = new Arc2D.Double(getX() + (getHeight()/1.5),getY() + (getHeight()/2),getWidth()/2,getHeight()/2,RIGHT_LEG_START,LEG_ANGLE,Arc2D.OPEN);
	Arc2D.Double leg6 = new Arc2D.Double(getX() + (getHeight()/1.5),getY() + (getHeight() - getHeight()/4),getWidth()/2,getHeight()/2,RIGHT_LEG_START,LEG_ANGLE,Arc2D.OPEN);
	
	g2.setColor(Color.BLACK);
	g2.draw(leg1);
	g2.draw(leg2);
	g2.draw(leg3);
	g2.draw(leg4);
	g2.draw(leg5);
	g2.draw(leg6);
	g2.setColor(Color.RED);
	g2.fill(oval) ;
	}
}