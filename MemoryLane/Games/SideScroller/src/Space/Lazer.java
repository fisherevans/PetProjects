package Space;
import java.awt.*;

import javax.swing.ImageIcon;

public class Lazer {
	int x,y;
	Image img;
	boolean visible;
	
	public Lazer(int startX, int startY) {
		x = startX;
		y = startY;
		
		ImageIcon newLazer = new ImageIcon(getClass().getResource("lazer.png"));
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
		y = y - 4;
		if(y < -img.getHeight(null))
			visible = false;
	}
}
