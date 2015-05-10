package Space;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class Board extends JPanel implements ActionListener
{
	Ship p;
	public Image img;
	Timer time;
	
	public Board() 
	{
		p = new Ship();
		addKeyListener(new AL());
		setFocusable(true);
		ImageIcon i = new ImageIcon(getClass().getResource("bg.jpg"));
		img = i.getImage();
		time = new Timer(5, this);
		time.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		ArrayList lazers = Ship.getLazers();
		for(int w = 0; w < lazers.size();w++)
		{
			Lazer m = (Lazer) lazers.get(w);
			if(m.getVisible())
				m.move();
			else
					lazers.remove(w);
		}
		
		p.move();
		repaint();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.drawImage(img,0,0,null);
		g2d.drawImage(p.getImage(),p.getX(),p.getY(),null);
		
		ArrayList lazers = Ship.getLazers();
		for(int w = 0; w < lazers.size();w++)
		{
			Lazer m = (Lazer) lazers.get(w);
			g2d.drawImage(m.getImage(),m.getX(),m.getY(),null);
		}
	}
	
	private class AL extends KeyAdapter
	{
		public void keyReleased(KeyEvent e)
		{
			p.keyReleased(e);
		}
		public void keyPressed(KeyEvent e)
		{
			p.keyPressed(e);
		}
	}
}
