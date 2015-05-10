package game;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game extends JPanel implements ActionListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	
	static Player player;
	static int playerSize = 30;
	
	Timer gameClock;
	
	boolean up, down, left, right;
	
	public Game()
	{
		setBackground(new Color(20,20,20)); // Background color
		setBounds(0,0,600,600);
		setFocusable(true);
		addKeyListener(this);
		
		player = new Player(40, 40, playerSize);
		
		up = false;
		down = false;
		left = false;
		right = false;

			// Set and start the clock
		gameClock = new Timer(10, this);
		gameClock.start();
		
		repaint();
	}

		// Paint Objects
	public void paint(Graphics g)
	{
			// Create the painter
		super.paint(g);
		if(g instanceof Graphics2D)
		{
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		
		g.setColor(new Color(0, 153, 255));
		g.fillRect((int)(player.getX()-(playerSize/2)), (int)(player.getY()-(playerSize/2)), playerSize, playerSize);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		player.updateMovement();
		
		repaint();
	}


	public void keyPressed(KeyEvent e)
	{
		System.out.println("Pressed!");
		
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			up = true;
		else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
			down = true;
		else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			left = true;
		else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			right = true;
		player.updateControls(up, down, left, right);
	}
	public void keyReleased(KeyEvent e)
	{
		System.out.println("Released!");
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
			up = false;
		else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
			down = false;
		else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
			left = false;
		else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
			right = false;
		player.updateControls(up, down, left, right);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
