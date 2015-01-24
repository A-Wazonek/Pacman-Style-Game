import javax.swing.* ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.geom.Ellipse2D ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;
import java.awt.event.MouseAdapter ;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.Rectangle ;

/**
 * @author Alex Wazonek
 * Draws and moves the Prey and Predators
 */

public class PacManComponent extends JComponent
{
	private final int PREDATOR_SIZE = 50;
	Predator player;
	private final int TIMER_DELAY = 50;
	private double timeRanSeconds = 0.0;
	private double timeRanMinutes = 0.0;
	private ArrayList<MoveableShape> creatures = new ArrayList<MoveableShape>();
	Random random = new Random();
	private int numberOfCreatures;
	private final int MINIMUM_CREATURES = 20;
	private final int MAXIMUM_CREATURES = 40;
	private int x;
	private int y;
	private int sizeOfPrey;
	private final int MAX_SIZE_OF_PREY = 20;
	private final int MIN_SIZE_OF_PREY = 10;
	private int heightOfBox;
	private int widthOfBox;
	private int speed;
	private int dir;
	final int UP = 0;
	final int RIGHT = 1;
	final int DOWN = 2;
	final int LEFT = 3;
	final int NUMBER_OF_DIRECTIONS = 4;
	final int SLOW_SPEED = 1;
	final int EDGE_PROTECTION = 20; //used for placing the prey to make sure that the prey arent placed directly on the edge of the map
	final double MINUTE = 60.0;
	final double TIME_INCREMENT = 0.05;
	private boolean gameOver = false;
	private boolean gameStarted = false;
	/**
	 * This creates an arraylist of creatures, and determines if they're fast or slow randomly, before placing them at random points on the playing field. 
	 * It also creates the Predator class and places it in the middle of the field.
	 * @param height The height of the frame, so the program doesnt place any creatures outside of the frame. It's also used so that the prey know not to leave the boundaries
	 * @param width The width of the frame, functions the same as height but takes in the width of the frame instead. 
	 */
		public PacManComponent(int width, int height)
		{
			numberOfCreatures = random.nextInt(MAXIMUM_CREATURES - MINIMUM_CREATURES) + MINIMUM_CREATURES; //Creates a random amount of creatures, somewhere between MINIMUM_CREATURES and MAXIMUM_CREATURES
			heightOfBox = height; //REPLACE CONSTANTS WITH VARIABLES
			widthOfBox = width;
			for(int i = 0; i < numberOfCreatures; i++)
			{
			x = random.nextInt(widthOfBox- EDGE_PROTECTION); //20 is to prevent them from being right on the edge
			y = random.nextInt(heightOfBox - EDGE_PROTECTION); //used for placing the creatures
			speed = random.nextInt(2);
			speed++; //prevents giving a creature 0 movement speed 
			dir = random.nextInt(NUMBER_OF_DIRECTIONS);
			sizeOfPrey = random.nextInt(MAX_SIZE_OF_PREY - MIN_SIZE_OF_PREY) + MIN_SIZE_OF_PREY;
			if(speed == SLOW_SPEED)
			{
				SlowPrey creature = new SlowPrey(x,y,sizeOfPrey,sizeOfPrey,speed,dir,widthOfBox,heightOfBox); //location of prey, size of prey, the direction the prey will be moving in, the height and width of the frame the shape has to move in
				creatures.add(creature);
			}
			else
			{
				FastPrey creature = new FastPrey(x,y,sizeOfPrey,sizeOfPrey,speed,dir,widthOfBox,heightOfBox);
				creatures.add(creature);
			}
			}
			dir = DOWN; //set direction to down, just used to give the player a starting direction. Can be changed later.
			player = new Predator((widthOfBox/2),(heightOfBox/2),PREDATOR_SIZE,PREDATOR_SIZE,dir,widthOfBox,heightOfBox);

		
		/**
		 * This creates the ActionListener which is activated by the timer
		 */
		class TaskPerform implements ActionListener
		{
			/**
			 * This is called by the timer, and every time the timer activates it runs through the arrayList of the Prey and moves them, then it checks to see if any of the prey are contained in the predator, and it removes them from the array list if they are (they are effectively dead). 
			 * Then the prey is moved. 
			 * @param event the event that caused action performed to activate (in this case, it will always be the timer ticking)
			 */
			public void actionPerformed(ActionEvent event)
			{
				if(gameStarted)
				{
					if(creatures.size() > 0) // if there are still prey alive, then they are moved. Otherwise the game is over
					{
					for(int i = 0; i<creatures.size();i++)
					{
						creatures.get(i).move();
						if(((Creature)creatures.get(i)).getSpeed() > 1)
							creatures.get(i).collide(player); //Checks if the predator is a fast prey, and if it is it runs away
						if(player.collide(creatures.get(i))) //if the prey are touching the predator, they're removed from the array list and killed. 
							creatures.remove(i);
						
					}
					timeRanSeconds = timeRanSeconds + TIME_INCREMENT;
					if(timeRanSeconds >= MINUTE)
					{
						timeRanSeconds = 0.0; //reset the number of seconds, changes 60 seconds into 1 minute and 0 seconds. 
						timeRanMinutes++;
					}
					player.move();
					}
					else
						gameOver = true;
				}
				else
					timeRanSeconds = 0.0;
				
				repaint();
			}
		}
		ActionListener taskPerform = new TaskPerform();
		final Timer timer = new Timer(TIMER_DELAY, taskPerform);
		
		/**
		 * Mouse Listener that takes in mouse button presses and either moves the player or start the game
		 */
		class MyMouseListener implements MouseListener
		{
			/**
			 * When a mouse button is clicked, it determines if the game has either started or finished and reacts accordingly. If the game has not started, it is set to started. 
			 * If the game has started and the game isnt over yet, it moves the Predator either clockwise or counterclockwise depending on what mouse button was pressed. 
			 * If the game has started, and the game has ended (all prey are gone) then the mouse buttons do nothing and the timer is stopped. 
			 * @param event Takes in what button has been pressed
			 */
			public void mouseClicked(MouseEvent event)
			{
				
				if(gameStarted && !gameOver)
				{
				if(event.getButton() == MouseEvent.BUTTON3) //Turns the predator Clockwise
				{
					int dir = player.getDirection();
					if(dir >= LEFT)
						player.setDirection(UP);
					else
						player.setDirection(++dir);
					player.directionChanged();
				}
				else if(event.getButton() == MouseEvent.BUTTON1)//Turns the predator Counter Clockwise
				{
					int dir = player.getDirection();
					if(dir > UP)
						player.setDirection(--dir);
					else
						player.setDirection(LEFT);
					player.directionChanged();
				}
				}
				else if(!gameOver)
				{
					timer.start();
					gameStarted = true;
				}
				repaint();
			}
			
			/**
			 * None of these are used in this program, and are just here to prevent any errors. 
			 */
			 public void mouseReleased(MouseEvent event){}
			 public void mousePressed(MouseEvent event){}
			 public void mouseEntered(MouseEvent event){}
			 public void mouseExited(MouseEvent event){}
		}
		
		MouseListener mouseListener = new MyMouseListener();
		addMouseListener(mouseListener);
		}
		
		/**
		 * Goes through the arraylist and prints out each prey, then prints out the predator and draws a box that shows the play area.
		 * @param g the graphical component
		 */
		public void paintComponent(Graphics g)
	    {
		Graphics2D g2 = (Graphics2D) g ;
		for (int i = 0; i < creatures.size(); i++) {
				creatures.get(i).draw(g2);
		} 
		player.draw(g2);
		g2.setColor(Color.BLACK);
		g2.drawString((int)timeRanMinutes + " m " + (int)timeRanSeconds + " s", 0, 10);
		Rectangle newRect = new Rectangle(0,0,widthOfBox,heightOfBox);
		g2.draw(newRect);
	    } 
}