package Game;
import java.awt.*;

import javax.swing.ImageIcon;

public class Enemy {
	int y;
	double dx = 0.15;
	double x;
	static Image img;
	boolean visible;
	double health;
	
	public Enemy(int startX, int startY, double startHealth) {
		x = startX;
		y = startY;
		health = startHealth;
		
		ImageIcon newLazer = new ImageIcon(getClass().getResource("enemy.png"));
		img = newLazer.getImage();
		
		visible = true;
		
	}

	public int getX()
	{
		return (int)x;
	}
	public int getY()
	{
		return y;
	}
	public boolean getVisible()
	{
		return visible;
	}
	public Image getImage()
	{
		return img;
	}
	
	public void move() {
		y = y + 1;
		if(y > 600)
			visible = false;
		if(Math.random() < 0.005)
		{
			dx = dx * (-1);
		}
		x = x + dx;
		if(x > 590 - img.getHeight(null))
			x = 590 - img.getHeight(null);
		if(x < 10)
			x = 10;
	}
	public void setVisible(boolean huh)
	{
		visible = huh;
	}
	public Rectangle getBound()
	{
		return new Rectangle((int)x, (int)y, img.getWidth(null), img.getHeight(null));
	}
}
