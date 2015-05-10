package Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;





public class Control extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	public Image bgImg,shipImg,ammoImg,healthImg;
	ImageIcon imgTemp;
	Timer rePainter;
	
	
	static boolean up,down,left,right,space;
	static int bgy,shipx,shipy;
	boolean gameOver = false;
	
	
	static Font font = new Font("SanSerif", Font.PLAIN, 12);
	int score;
	
	public Control()
	{
		imgTemp = new ImageIcon(getClass().getResource("bg.jpg"));
		bgImg = imgTemp.getImage();

		imgTemp = new ImageIcon(getClass().getResource("ship.png"));
		shipImg = imgTemp.getImage();
		
		imgTemp = new ImageIcon(getClass().getResource("ammo.png"));
		ammoImg = imgTemp.getImage();
		
		imgTemp = new ImageIcon(getClass().getResource("health.png"));
		healthImg = imgTemp.getImage();

		addKeyListener(new AL());
		setFocusable(true);
		
		rePainter = new Timer(5, this);
		rePainter.start();
	}
	
	public void paint(Graphics g)
	{
		if(gameOver)
		{
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;
			g.setColor(Color.WHITE);
	
			g2d.drawImage(bgImg,0,bgy,null);
			
			Font font = new Font("SanSerif", Font.BOLD, 48);
			
			g2d.setFont(font);
			g2d.drawString("You Died!", 20,68);
			g2d.drawString("Score: " + score, 20,138);
			rePainter.stop();
		}
		else
		{
			super.paint(g);
			Graphics2D g2d = (Graphics2D) g;
	
			g2d.drawImage(bgImg,0,bgy,null);
	
			
			for(int w = 0; w < Spawner.lazerList.size();w++)
			{
				Lazer m = (Lazer) Spawner.lazerList.get(w);
				if(m.getVisible())
				{
					m.move();
					g2d.drawImage(m.getImage(),m.getX(),m.getY(),null);
				}
				else
				{
					Spawner.lazerList.remove(w);
					w--;
				}
			}
			
			for(int w = 0; w < Spawner.enemyLazerList.size();w++)
			{
				EnemyLazer m = (EnemyLazer) Spawner.enemyLazerList.get(w);
				if(m.getVisible())
				{
					m.move();
					g2d.drawImage(m.getImage(),m.getX(),m.getY(),null);
				}
				else
				{
					Spawner.enemyLazerList.remove(w);
					w--;
				}
			}
			
			for(int w = 0; w < Spawner.ammoList.size();w++)
			{
				Ammo m = (Ammo) Spawner.ammoList.get(w);
				if(m.getVisible())
				{
					m.move();
					g2d.drawImage(m.getImage(),m.getX(),m.getY(),null);
				}
				else
				{
					Spawner.ammoList.remove(w);
					w--;
				}
			}
			
			for(int w = 0; w < Spawner.healthList.size();w++)
			{
				Health m = (Health) Spawner.healthList.get(w);
				if(m.getVisible())
				{
					m.move();
					g2d.drawImage(m.getImage(),m.getX(),m.getY(),null);
				}
				else
				{
					Spawner.healthList.remove(w);
					w--;
				}
			}
			
			for(int w = 0; w < Spawner.enemyList.size();w++)
			{
				Enemy m = (Enemy) Spawner.enemyList.get(w);
				if(m.getVisible())
				{
					m.move();
					g2d.drawImage(m.getImage(),m.getX(),m.getY(),null);
				}
				else
				{
					Spawner.enemyList.remove(w);
					w--;
					score = score - 5;
				}
			}
	
			g2d.drawImage(shipImg,shipx,shipy,null);
			
			g.setColor(Color.WHITE);
			g.drawRect(5,585,150,10);
			if(Spawner.ammo > 0)
				g.fillRect(7,587,(int)(147*Spawner.ammo),7);
			else
				g.fillRect(7,587,1,7);
			
			g.drawRect(445,585,150,10);
			if(Spawner.health > 0)
				g.fillRect(447+147 - (int)(147*Spawner.health),587,(int)(147*Spawner.health),7);
			else
				g.fillRect(447,587,1,7);
			
			g2d.setFont(font);
			g2d.drawString("Score: " + score, 5,14);
			
			g2d.drawImage(ammoImg,162,585,null);
			g2d.drawImage(healthImg,430,585,null);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		repaint();
		checkCollisions();
		Spawner.ammo = Spawner.ammo + 0.0003;
		if(Spawner.ammo > 1)
			Spawner.ammo = 1;
		Spawner.health = Spawner.health + 0.0001;
		if(Spawner.health > 1)
			Spawner.health = 1;
		/*System.out.printf("Ammo:%6d",Spawner.ammoList.size());
		System.out.printf(" | Health:%6d",Spawner.healthList.size());
		System.out.printf(" | Enemy:%6d",Spawner.enemyList.size());
		System.out.printf(" | Enemy Lazers:%6d",Spawner.enemyLazerList.size());
		System.out.printf(" | Lazers:%6d\n",Spawner.lazerList.size());*/
	}
	
	private class AL extends KeyAdapter
	{
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
			else if(key == KeyEvent.VK_SPACE)
				space = true;
			Ship.setKeys(up,down,left,right);
		}
		public void keyReleased(KeyEvent e)
		{
			int key = e.getKeyCode();
			if(key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
				up = false;
			else if(key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
				down = false;
			else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
				left = false;
			else if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
				right = false;
			else if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_D)
				space = false;
			Ship.setKeys(up,down,left,right);
		}
	}
	
	public void checkCollisions()
	{
		Rectangle shipBox = Ship.getBound();
		int[] shipXs = {shipBox.x+37, shipBox.x, shipBox.x+37, shipBox.x+75, shipBox.x+37}; 
		int[] shipYs = {shipBox.y,shipBox.y+31,shipBox.y+63,shipBox.y+31,shipBox.y}; 
		Polygon shipOccupy = new Polygon(shipXs,shipYs,5);
				
		for(int w = 0; w < Spawner.ammoList.size();w++)
		{
			Ammo m = (Ammo) Spawner.ammoList.get(w);
			Rectangle m1 = m.getBound();
			if(shipOccupy.intersects(m1))
			{
				m.setVisible(false);
				Spawner.ammo = Spawner.ammo + 0.45;
			}
		}

		for(int w = 0; w < Spawner.healthList.size();w++)
		{
			Health m = (Health) Spawner.healthList.get(w);
			Rectangle m1 = m.getBound();
			if(shipOccupy.intersects(m1))
			{
				m.setVisible(false);
				Spawner.health = Spawner.health + 0.5;
			}
		}
		
		for(int w = 0; w < Spawner.enemyLazerList.size();w++)
		{
			EnemyLazer m = (EnemyLazer) Spawner.enemyLazerList.get(w);
			Rectangle m1 = m.getBound();
			if(shipOccupy.intersects(m1))
			{
				m.setVisible(false);
				Spawner.health = Spawner.health - 0.06;
				if(Spawner.health <= 0)
				{
					gameOver = true;
				}
			}
		}
		
		for(int w = 0; w < Spawner.enemyList.size();w++)
		{
			Enemy m = (Enemy) Spawner.enemyList.get(w);
			Rectangle m1 = m.getBound();
			int[] enemyShipXs = {m1.x+38, m1.x, m1.x+37, m1.x+75, m1.x+38}; 
			int[] enemyShipYs = {m1.y+8,m1.y+39,m1.y+71, m1.y+39,m1.y+8}; 
			Polygon enemyShipOccupy = new Polygon(enemyShipXs,enemyShipYs,5);
			
			if(shipOccupy.intersects(enemyShipOccupy.getBounds2D()))
			{
				m.setVisible(false);
				Spawner.health = Spawner.health - 0.2;
				if(Spawner.health <= 0)
				{
					gameOver = true;
				}
			}
			
			for(int b = 0; b < Spawner.lazerList.size();b++)
			{
				Lazer n = (Lazer) Spawner.lazerList.get(b);
				Rectangle n1 = n.getBound();
				if(m1.intersects(n1))
				{
					m.health = m.health - 0.35;
					n.setVisible(false);
					if(m.health <= 0)
					{
						m.setVisible(false);
						score = score + 25;
					}
				}
			}
			
			if(Math.random() < Spawner.enemyLazerChance)
			{
				Spawner.newEnemyLazer((int)(m.getX()+(m.img.getWidth(null)/2)) - 2,m.getY()+m.img.getHeight(null)-4);
			}
			
		}
		
	}

	public static void setbgy(int temp)
	{
		bgy = temp;
	}
	public static void setshipx(int temp)
	{
		shipx = temp;
	}
	public static void setshipy(int temp)
	{
		shipy = temp;
	}

}
