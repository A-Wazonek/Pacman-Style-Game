import java.awt.geom.Ellipse2D ;
import java.awt.geom.Arc2D ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.Color ;
import java.awt.Rectangle ;
import java.awt.BasicStroke ;
import java.util.Random;
import java.awt.Color ;

/**
 * @author Alex Wazonek
 * The Creature class is the super class for each prey and predator, and it implements MoveableShape. 
 */
public class Creature implements MoveableShape
{
	int x;
	int y;
	int height;
	int width;
	int speed;
	int direction;
	int frameHeight;
	int frameWidth;
	Rectangle boundingBox;
	final int LEFT = 3;
	final int UP = 0;
	final int DOWN = 2;
	final int RIGHT = 1;

	/**
	 * Initializes the creature
	 * @param x the X location on the playing field
	 * @param y the Y location on the playing field
	 * @param w the width of the creature, used for implementing the bounding box
	 * @param h the height of the creature
	 * @param speed how fast the creature is
	 * @param dir the direction the creature is facing
	 * @param framew the width of the playing field, used to determing where the prey and predator cannot go. 
	 * @param frameh the height of the playing field
	 */
	public Creature(int x, int y, int width, int height, int speed, int dir,int frameWidth,int frameHeight)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		direction = dir;
		this.frameHeight = frameHeight;
		this.frameWidth = frameWidth;
		boundingBox = new Rectangle(x,y,width,height);
	}
	
	/**
	 * Moves the creature, overriden by the prey or predator's move method as they have their own unique movement patterns
	 */
	public void move()
	{
		
	}
	
	/**
	 * Sets the direction the the given integer
	 *@param dir the new direction
	 */
	public void setDirection(int dir)
	{
		if(!(dir > LEFT || dir < UP))
			direction = dir;
	}
	
	/**
	 * Overriden by the prey or predator's collide method, so this is unused and returns false always
	 * @param other the other MoveableShape object that is checked to see if it is colliding with
	 * @return always return false, as this method should not be called except by the Fast prey and Predator
	 */
	public boolean collide(MoveableShape other)
	{
		return false;
	}
	
	/**
	 * Draws the creature, but since each creature will look different this is left blank and should be overwritten by predator and prey
	 * @param g2 the graphics context
	 */
	public void draw(Graphics2D g2)
	{

	}
	
	/**
	 * Overriden by the prey/predators methods, used to see if the creature is in the box. 
	 */
	public void checkBounds()
	{
		
	}
	
	/**
	 * Gets the X coordinate of the creature
	 *@return the X coordinate of the creature
	 */
    public int getX() 
    { 
    	return x; 
    }
    
    /**
     * Gets the Y coordinate of the creature
     * @return the Y coordinate of the creature
     */
    public int getY()
    { 
    	return y; 
    }
    
    /**
     * Gets the Width of the creature's bounding box (effectively the width of the creature itself)
     * @return the width of the creature
     */
    public int getWidth()
    {
    	return width; 
    }
    
    /**
     * Gets the Height of the creature's bounding box (effectively the width of the creature itself)
     * @return the height
     */
    public int getHeight() 
    {
    	return height; 
    }
    
    /**
     * Gets the direction the creature is facing
     * @return the direction the creature is facing
     */
    public int getDirection()
    {
    	return direction;
    }
    
    /**
     * Gets the Height of the playing field
     * @return the frameHeight
     */
    public int getFrameHeight()
    {
    	return frameHeight;
    }
    
    /**
     * Gets the Width of the playing field
     * @return the frameWidth
     */
    public int getFrameWidth()
    {
    	return frameWidth;
    }
    
    /**
     * Gets the speed of the creature
     * @return the speed
     */
    public int getSpeed()
    {
    	return speed;
    }
}