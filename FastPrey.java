import javax.swing.* ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.geom.Ellipse2D ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseMotionListener ;
import java.util.Random;
import java.awt.Color ;
import java.awt.Rectangle ;
import java.awt.geom.Arc2D ;
import java.awt.BasicStroke ;

/**
 * @author Alex Wazonek
 * Creates a fast moving prey that looks like a frowny face
 */
public class FastPrey extends Creature
{
	Random random = new Random();
	int newDir;
	int SPEED = 2;
	final int UP = 0;
	final int RIGHT = 1;
	final int DOWN = 2;
	final int LEFT = 3;
	boolean outOfBounds = false;
	Rectangle range;
	boolean running = false;
	final static BasicStroke thinStroke = new BasicStroke(1.0f) ;
	final int MAX_MOVEMENT = 15; //used to determine how often the prey will change direction
	final int RANGE_SIZE = 100;
	final int MAX_DIRECTIONS = 4;
	final int BOUNDARY_RANGE = 10;
	final int EDGE_OF_FIELD = 0;
	final int EYE_DIMENSIONS = 2;
	final int MOUTH_ANGLE = 180;
	final int MOUTH_START = 0;
	
	/**
	 * Creates the predator, calls the super class and constructs that
	 * @param x the X location on the playing field
	 * @param y the Y location on the playing field
	 * @param w the width of the creature, used for implementing the bounding box
	 * @param h the height of the creature
	 * @param speed how fast the creature is
	 * @param dir the direction the creature is facing
	 * @param framew the width of the playing field, used to determing where the prey and predator cannot go. 
	 * @param frameh the height of the playing field
	 */
	public FastPrey(int x, int y, int w, int h, int speed, int dir,int framew, int frameh)
	{
		super(x,y,w,h,speed,dir,framew,frameh);
	}
	
	/**
	 * Moves the prey by pixels equal to SPEED (2 in this case), or SPEED + 1 if its running away from the predator
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
	 * Randomly gives the prey a new direction to move in to prevent it from only moving in one direction.
	 * If the prey is touching the edge of the map it will not give it a random direction, instead it will keep moving in the direction it is in until it is within the BOUNDARY_RANGE again.  
	 */
	public void newDirection()
	{
		if(!outOfBounds)
		{
			newDir = random.nextInt(MAX_MOVEMENT);
			if(newDir == getDirection())
				setDirection(random.nextInt(MAX_DIRECTIONS));
		}
		else if(((y > BOUNDARY_RANGE) && (x > BOUNDARY_RANGE)) || ((x < getFrameWidth() - BOUNDARY_RANGE) && (y < getFrameHeight() - BOUNDARY_RANGE)))
				outOfBounds = false;
	}
	
	/**
	 * Checks if the predator is within certain bounds (determined by the rectangle range). If it is, the prey run away in the opposite direction of it. 
	 * @param other the predator
	 */
	public boolean collide(MoveableShape other)
	{
		Creature predator = (Creature)other;
		range = new Rectangle(getX() - (RANGE_SIZE/2 - getHeight()/2), getY() - (RANGE_SIZE/2 - getHeight()/2), RANGE_SIZE, RANGE_SIZE);
		int distanceX = getX() - predator.getX();
		int distanceY = getY() - predator.getY();
		if(range.contains(predator.getX(),predator.getY()) || range.contains(predator.getX() + predator.getWidth(), predator.getY() + predator.getHeight()))
		{
			if(distanceX > distanceY)
			{
				if(distanceX > 0)
				{
					if(!outOfBounds)
						setDirection(RIGHT);
				}
				else
				{
					if(!outOfBounds)
						setDirection(LEFT);
				}
			}
			else
			{
				if(distanceY < 0)
				{
					if(!outOfBounds)
						setDirection(UP);
				}
				else
				{
					if(!outOfBounds)
						setDirection(DOWN);
				}
			}
			running = true;
			
		}
		else
			running = false;
		return running;
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
	 * Draws the Fast Prey, which is a Green circle with a frowny face on it. 
	 * @param g2 the graphics context
	 */
	public void draw(Graphics2D g2)
	{
	Ellipse2D.Double body = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight()) ;
	g2.setColor(Color.GREEN);
	g2.fill(body) ;
	Ellipse2D.Double eye1 = new Ellipse2D.Double(getX() + getWidth()/4, getY() + (getHeight()/3), EYE_DIMENSIONS, EYE_DIMENSIONS) ;
	Ellipse2D.Double eye2 = new Ellipse2D.Double(getX() + getWidth()/1.5, getY() + (getHeight()/3), EYE_DIMENSIONS, EYE_DIMENSIONS) ;
	Arc2D.Double mouth = new Arc2D.Double(getX() + getWidth()/4,getY() + (getHeight()/2),getWidth()/2,getHeight()/2,MOUTH_START,MOUTH_ANGLE,Arc2D.OPEN);
	g2.setColor(Color.BLACK);
	g2.draw(mouth);
	g2.fill(eye1);
	g2.fill(eye2);
	}
	
}