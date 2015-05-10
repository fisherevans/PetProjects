package Game;
import java.awt.*;

import javax.swing.ImageIcon;

public class Ammo {
	int x,y;
	static Image img;
	boolean visible;
	
	public Ammo(int startX, int startY) {
		x = startX;
		y = startY;
		
		ImageIcon newLazer = new ImageIcon(getClass().getResource("ammo_item.png"));
		img = newLazer.getImage();
		
		visible = true;
		
	}

	public int getX()
	{
		return x;
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
