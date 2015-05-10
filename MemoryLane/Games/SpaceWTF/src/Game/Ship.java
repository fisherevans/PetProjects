package Game;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;



public class Ship implements ActionListener
{
	static public Image shipImg;
	ImageIcon imgTemp;
	Timer shipT;
	
	double dx,dy;
	static double x,y;
	static double changeX,changeY = 0;
	double a = 0.2;
	static int tv = 2;
	static boolean left,right,up,down;
	
	public Ship()
	{
		imgTemp = new ImageIcon(getClass().getResource("ship.png"));
		shipImg = imgTemp.getImage();

		x = 600/2 - shipImg.getWidth(null)/2;
		y = 600 - shipImg.getHeight(null) - 10;
		
		shipT = new Timer(5, this);
		shipT.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		move();

		horizMovement();
		vertMovement();
		
		
		Control.setshipx((int)x);
		Control.setshipy((int)y);
		
		// System.out.println(dx + " " + dy); // Debug
	}
	
	public void move()
	{
		if(dx < 0)
			changeX = Math.ceil(dx);
		else
			changeX = Math.floor(dx);
		x = x + changeX;
		
		if(x > 515)
			x = 515;
		else if(x < 10)
			x = 10;

		if(dy < 0)
			changeY = Math.ceil(dy);
		else
			changeY = Math.floor(dy);
		y = y + changeY;
		
		if(y > 515)
			y = 515;
		else if(y < 10)
			y = 10;
		
	}
	
	public void horizMovement()
	{
		if(right && !left)
			dx = dx + a;
		else if(left && !right)
			dx = dx - a;
		else
			dx = dx / 1.02;
		
		if(Math.abs(dx) < a)
			dx = 0;
		
		if(dx > tv)
			dx = tv;
		if(dx < -tv)
			dx = -tv;
	}
	
	public void vertMovement()
	{
		if(down && !up)
			dy = dy + a;
		else if(up && !down)
			dy = dy - a;
		else
			dy = dy / 1.02;
		
		if(Math.abs(dy) < a)
			dy = 0;
		
		if(dy > tv)
			dy = tv;
		if(dy < -tv)
			dy = -tv;
	}
	
	public static void setKeys(boolean upN, boolean downN, boolean leftN, boolean rightN)
	{
		left = leftN;
		right = rightN;
		up = upN;
		down = downN;
	}
	
	public static int getX()
	{
		return (int)x;
	}
	public static int getY()
	{
		return (int)y;
	}
	public static Rectangle getBound()
	{
		return new Rectangle((int)x, (int)y, shipImg.getWidth(null), shipImg.getHeight(null));
	}

}
