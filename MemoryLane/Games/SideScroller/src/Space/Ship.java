package Space;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ship {
	int y, x, dl, dr, dd, du, dy, dx;
	Image ship;
	
	static ArrayList lazers;
	
	public Ship()
	{
		ImageIcon i = new ImageIcon(getClass().getResource("ship.png"));
		ship = i.getImage();
		x = (600/2) - (ship.getWidth(null)/2);
		y = 600 - ship.getHeight(null) - 10;
		lazers = new ArrayList();
	}
	
	public static ArrayList getLazers()
	{
		return lazers;
	}
	
	public void fire()
	{
		int tempX = x + (ship.getWidth(null)/2) - 1;
		Lazer z = new Lazer(tempX - 10, y-5);
		Lazer z2 = new Lazer(tempX + 9, y-5);
		lazers.add(z);
		lazers.add(z2);
	}
	
	public void move()
	{
		x = x + dx;
		if(x < 10)
			x = 10;
		if(x > 590-ship.getWidth(null))
			x = 590-ship.getWidth(null);

		y = y + dy;
		if(y < 10)
			y = 10;
		if(y > 590-ship.getWidth(null))
			y = 590-ship.getWidth(null);
	}

	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public Image getImage()
	{
		return ship;
	}
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT)
		{
			dl = 2;
		}
		else if(key == KeyEvent.VK_RIGHT)
		{
			dr = 2;
		}
		else if(key == KeyEvent.VK_UP)
		{
			du = 2;
		}
		else if(key == KeyEvent.VK_DOWN)
		{
			dd = 2;
		}
		else if(key == KeyEvent.VK_SPACE)
		{
			fire();
		}
		
		dx = dr - dl;
		dy = dd - du;
	}
	
	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_LEFT)
		{
			dl = 0;
		}
		else if(key == KeyEvent.VK_RIGHT)
		{
			dr = 0;
		}
		else if(key == KeyEvent.VK_UP)
		{
			du = 0;
		}
		else if(key == KeyEvent.VK_DOWN)
		{
			dd = 0;
		}
		
		dx = dr - dl;
		dy = dd - du;
	}
}
