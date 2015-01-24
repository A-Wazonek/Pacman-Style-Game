import javax.swing.* ;
import java.awt.Graphics ;
import java.awt.Graphics2D ;
import java.awt.geom.Ellipse2D ;
import java.awt.event.MouseEvent ;
import java.awt.event.MouseListener ;
import java.awt.event.MouseAdapter ;
import java.awt.event.MouseMotionListener ;
import java.awt.Color ;

/**
 * @author Alex Wazonek
 * An interface used to create creatures
 * It has move, draw and collide. 
 */
public interface MoveableShape
{
	void move();
	void draw(Graphics2D g2);
	boolean collide(MoveableShape other);
}