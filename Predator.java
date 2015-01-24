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
 * @auther Alex Wazonek
 * Creates the player's predator object
 */
public class Predator extends Creature
{
	Random random = new Random();
	int newDir;
	final int SPEED = 4;
	final int UP = 0;
	final int RIGHT = 1;
	final int DOWN = 2;
	final int LEFT = 3;
	boolean stop = false;
	boolean outOfBounds = false;
	final int MOUTH_ANGLE = 115;
	final static BasicStroke thinStroke = new BasicStroke(1.0f) ;
	final int EYE_LOCATION_UP_Y = 10;
	final int EYE_LOCATION_DOWN_Y = 30;
	final int EYE_LOCATION_LEFT_Y = 10;
	final int EYE_LOCATION_RIGHT_Y = 10;
	final int EYE_DIMENSIONS = 5;
	final int EYE_LOCATION_UP_X = 15;
	final int EYE_LOCATION_DOWN_X = 15;
	final int EYE_LOCATION_LEFT_X = 15;
	final int EYE_LOCATION_RIGHT_X = 30;
	final int MOUTH_LOCATION_UP_X = 15;
	final int MOUTH_LOCATION_DOWN_X = 15;
	final int MOUTH_LOCATION_LEFT_X = 0;
	final int MOUTH_LOCATION_RIGHT_X = 30;
	final int MOUTH_LOCATION_UP_Y = 0;
	final int MOUTH_LOCATION_DOWN_Y = 30;
	final int MOUTH_LOCATION_LEFT_Y = 10;
	final int MOUTH_LOCATION_RIGHT_Y = 10;
	final int MOUTH_START_UP = 315;
	final int MOUTH_START_DOWN = 335;
	final int MOUTH_START_LEFT = 225;
	final int MOUTH_START_RIGHT = 180;
	
	
	
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
	public Predator(int x, int y, int w, int h, int dir, int framew, int frameh)
	{
		super(x,y,w,h,3,dir,framew,frameh);
	}
	
	/**
	 * Moves the predator based on which direction it is facing. Doesnt move the predator if it is moving into the edge of the map. 
	 */
	public void move()
	{
		if(!stop){
		if(getDirection() == UP)
		{
			y-= SPEED;
		}
		else if(getDirection() == RIGHT)
		{
			x+= SPEED;
		}
		else if(getDirection() == DOWN)
		{
			y+= SPEED;
		}
		else if(getDirection() == LEFT)
		{
			x-= SPEED;
		}
		boundingBox = new Rectangle(getX(),getY(),getWidth(),getHeight());
		checkBounds();}
	}
	
	/**
	 * Allows the predator to move again if it was moving into the edge of the map
	 */
	public void directionChanged()
	{
		stop = false;
	}
	
	/**
	 * Checks if the predator is touching the boundaries of the box
	 */
	public void checkBounds()
	{
		if(y <= 0 && (getDirection() == UP))
		{
			stop = true;
		}
		if(y >= getFrameHeight() - getHeight() && (getDirection() == DOWN))
		{
			stop = true;
		}
		if(x <= 0 && (getDirection() == LEFT))
		{
			stop = true;
		}
		if(x >= getFrameWidth() - getWidth() && (getDirection() == RIGHT))
		{
			stop = true;
		}
	}
	
	/**
	 * Checks to see if the predator has collided with a prey
	 * @param other: the prey that will be checked if it is within the Predator
	 * @return returns true if it has collided with a prey. 
	 */
	public boolean collide(MoveableShape other)
	{
		Creature prey = (Creature)other;
		if(boundingBox.contains(prey.getX(),prey.getY()) || boundingBox.contains(prey.getX() + prey.getWidth(), prey.getY() + prey.getHeight()))
			return true;
		else 
			return false;
	}
	
	/**
	 * Determinesthe X location of the eye based on which direction the predator is facing
	 * @return what X location the eye should be placed on. 
	 */
	public int directionModifierEyesX()
	{
		if(getDirection() == UP)
		{
			return EYE_LOCATION_UP_X;
		}
		if(getDirection() == RIGHT)
			return EYE_LOCATION_RIGHT_X;
		if(getDirection() == DOWN)
			return EYE_LOCATION_DOWN_X;
		else 
			return EYE_LOCATION_LEFT_X;
	}
	
	/**
	 * Determines the Y location of the eye based on which direction the predator is facing
	 * @return returns the Y location where the eye should be
	 */
	public int directionModifierEyesY()
	{
		if(getDirection() == UP)
		{
			return EYE_LOCATION_UP_Y;
		}
		if(getDirection() == RIGHT)
			return EYE_LOCATION_RIGHT_Y;
		if(getDirection() == DOWN)
			return EYE_LOCATION_DOWN_Y;
		else 
			return EYE_LOCATION_LEFT_Y;
	}
	
	/**
	 * Determines the X location of the mouth based on the direction the predator is facing
	 * @return the X location where the mouth should be
	 */
	public int mouthX()
	{
		if(getDirection() == RIGHT)
			return getX() + MOUTH_LOCATION_RIGHT_X;
		if(getDirection() == LEFT)
			return getX() + MOUTH_LOCATION_LEFT_X;
		if(getDirection() == UP)
			return getX() + MOUTH_LOCATION_UP_X;
		else return getX() + MOUTH_LOCATION_DOWN_X;
	}
	
	/**
	 * Determines the Y location of the mouth based on the direction the predator is facing
	 * @return the Y location where the mouth should be
	 */
	public int mouthY()
	{
		if(getDirection() == RIGHT)
			return getY() + MOUTH_LOCATION_RIGHT_Y;
		if(getDirection() == LEFT)
			return getY() + MOUTH_LOCATION_LEFT_Y;
		if(getDirection() == DOWN)
			return getY() + MOUTH_LOCATION_DOWN_Y;
		else return getY() + MOUTH_LOCATION_UP_Y;

	}
	
	/**
	 * Determines where the mouth's arc is started at based on the direction it is facing
	 * @return the point where the mouth starts. 
	 */
	public int startMouth()
	{
		if(getDirection() == RIGHT)
			return MOUTH_START_RIGHT;
		if(getDirection() == LEFT)
			return MOUTH_START_LEFT;
		if(getDirection() == UP)
			return MOUTH_START_UP;
		else return MOUTH_START_DOWN;
	}
	

	/**
	 * Draws the predators smiley face. 
	 * @param g2 the graphics context
	 */
	public void draw(Graphics2D g2)
	{
	Ellipse2D.Double body = new Ellipse2D.Double(getX(), getY(), getWidth(), getHeight()) ;
	Ellipse2D.Double eyes = new Ellipse2D.Double(getX() + directionModifierEyesX(), getY() + directionModifierEyesY(), EYE_DIMENSIONS,EYE_DIMENSIONS);
	g2.setColor(Color.YELLOW);
	g2.fill(body) ;
	g2.setColor(Color.BLACK);
	g2.fill(eyes);
	Arc2D.Double mouth = new Arc2D.Double(mouthX(),mouthY(),getWidth()/2,getHeight()/2,startMouth(),MOUTH_ANGLE,Arc2D.OPEN);
	g2.setStroke(thinStroke);
	g2.draw(mouth);
	}
	
}