import javax.swing.*;
import java.awt.*;

/**
 * @author Alex Wazonek
 * Puts up a window with a component that draws the creatures
 */
public class PacManViewer
{
	public static void main(String[] args)
	{
		final int WIDTH = 500;
		final int HEIGHT = 500;
		JFrame frame = new JFrame("Pac Man Game");
		PacManComponent component = new PacManComponent(WIDTH,HEIGHT);
		Dimension dimension = new Dimension(WIDTH,HEIGHT);
		component.setPreferredSize(dimension);
		frame.add(component);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}