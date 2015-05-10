package Game;
import java.awt.*;

import javax.swing.ImageIcon;

public class EnemyLazer {
	int x,y;
	Image img;
	boolean visible;
	
	public EnemyLazer(int startX, int startY) {
		x = startX;
		y = startY;
		
		ImageIcon newLazer = new ImageIcon(getClass().getResource("enemy_lazer.png"));
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
	public void setVisible(boolean huh)
	{
		visible = huh;
	}
	public Image getImage()
	{
		return img;
	}
	
	public void move() {
		y = y + 3;
		if(y > 600)
			visible = false;
	}
	public Rectangle getBound()
	{
		return new Rectangle((int)x, (int)y, img.getWidth(null), img.getHeight(null));
	}
}
