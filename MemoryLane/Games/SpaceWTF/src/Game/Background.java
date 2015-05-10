package Game;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;



public class Background implements ActionListener
{
	public Image bgImg1,bgImg2;
	ImageIcon imgTemp;
	Timer bgT;
	
	double y = -600;
	double delta = 0;
	
	public Background()
	{
		imgTemp = new ImageIcon(getClass().getResource("bg.jpg"));
		bgImg1 = imgTemp.getImage();
		bgImg2 = imgTemp.getImage();
		
		bgT = new Timer(5, this);
		bgT.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		move();
		Control.setbgy((int)Math.round(y));
	}
	
	public void move()
	{
		//delta = (int)((Ship.changeY/Ship.tv)*0.75);
		delta = 0.75 - (Ship.changeY/Ship.tv)*0.15;
		y = y + delta;
		if(y >= 0)
			y = -600;
	}

}
