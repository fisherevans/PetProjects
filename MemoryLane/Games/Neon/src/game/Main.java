package game;

import java.awt.Color;
import java.awt.Insets;

import javax.swing.JFrame;

public class Main extends JFrame
{
	public Main()
	{
		super("Neon | Game");
		setVisible(true);
		setLocation(100,100); // Center on screen
		Insets insets = getInsets(); // Get Insets
		setSize(insets.left + 600,insets.top + 600); // Insets + width and height
		setResizable(false);
		getContentPane().setBackground(new Color(20,20,20)); // Background color
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		
		Game game = new Game();
		add(game);
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
	}
}
