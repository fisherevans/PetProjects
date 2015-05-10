package info.fisherevans.imguruploader;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Crop extends JFrame
{
	private BufferedImage image;
	private PicturePane picturePane;
	private JButton cropButton, uploadButton;
	
	private float scale;
	private int x, y;
	
	private int cropX, cropY, cropW, cropH;

	private PictureMouseListener pictureML;
	
	public Crop(BufferedImage image)
	{
		cropX = 0;
		cropY = 0;
		cropW = 0;
		cropH = 0;
		
		setTitle("Imgur Uploader - Clipboard Crop");
		this.image = image;
		picturePane = new PicturePane();
		add(picturePane);
        setSize(800,600);
        setLocation(200,200);
        setVisible(true);
        
        pictureML = new PictureMouseListener();
        picturePane.addMouseMotionListener(pictureML);
        picturePane.addMouseListener(pictureML);
        
        cropButton = new JButton(new ImageIcon("src/images/crop.png"));
        cropButton.setOpaque(false);
        cropButton.setBorderPainted(false);
        cropButton.setContentAreaFilled(false);
        cropButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cropButton.setBorder(null);
        
        uploadButton = new JButton(new ImageIcon("src/images/upload.png"));
        uploadButton.setOpaque(false);
        uploadButton.setBorderPainted(false);
        uploadButton.setContentAreaFilled(false);
        uploadButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        uploadButton.setBorder(null);

        picturePane.add(cropButton);
        picturePane.add(uploadButton);
	}
    
    public class PicturePane extends JPanel
    {
        protected void paintComponent(Graphics g)
        {
        	g.setColor(Color.BLACK);
        	g.fillRect(0, 0, this.getWidth(), this.getHeight());
        	
        	float widthW = this.getWidth()-20;
        	float heightW = this.getHeight()-80;
        	float widthI = image.getWidth();
        	float heightI = image.getHeight();
				
			scale = 1;
			
			if(widthI > widthW)
				scale = (float)(widthW/widthI) < scale ? (float)(widthW/widthI) : scale;
			if(heightI > heightW)
				scale = (float)(heightW/heightI) < scale ? (float)(heightW/heightI) : scale;
				
			int x = (int)((widthW-(widthI*scale))/2.0)+10;
			int y = (int)((heightW-(heightI*scale))/2.0)+10;
			
			g.drawImage(image, x, y, (int)(widthI*scale), (int)(heightI*scale), this);
			
			g.setColor(new Color(0f, 0.6f, 1f, 0.5f));
			System.out.printf("%s %s %s %s\n",cropX, cropY, cropW, cropH);
			
			int drawX = cropX;
			int drawY = cropY;
			int drawW = cropW;
			int drawH = cropH;

			if(drawW < 0)
			{
				drawW *= -1;
				drawX -= drawW;
			}
			if(drawH < 0)
			{
				drawH *= -1;
				drawY -= drawH;
			}
				
			g.fillRect(drawX, drawY, drawW, drawH);
			g.setColor(new Color(0f, 0.6f, 1f, 1f));
			g.drawRect(drawX, drawY, drawW, drawH);
			
			g.setColor(new Color(0.1f, 0.1f, 0.1f));
			g.fillRect(0, this.getHeight() - 60, this.getWidth(), 60);
			cropButton.setBounds((this.getWidth()/2)-120, this.getHeight()-50, 100, 40);
			uploadButton.setBounds((this.getWidth()/2)+20, this.getHeight()-50, 100, 40);
        }
    }
    
    public class PictureMouseListener implements MouseMotionListener, MouseListener 
    {
		public void mousePressed(MouseEvent me)
		{
        	System.out.println("MOUSE "+me.getX()+" "+me.getY());
    		cropX = me.getX();
    		cropY = me.getY();
    		cropW = 0;
    		cropH = 0;
    		picturePane.repaint();
		}

		public void mouseReleased(MouseEvent me)
		{
        	System.out.println("MOUSE "+me.getX()+" "+me.getY());
    		cropW = me.getX() - cropX;
    		cropH = me.getY() - cropY;
    		picturePane.repaint();
		}

		public void mouseDragged(MouseEvent me)
		{
        	System.out.println("MOUSE "+me.getX()+" "+me.getY());
    		cropW = me.getX() - cropX;
    		cropH = me.getY() - cropY;
    		picturePane.repaint();
		}

		public void mouseMoved(MouseEvent e) { }

		public void mouseClicked(MouseEvent e)
		{
			
		}

		public void mouseEntered(MouseEvent e)
		{
			
		}

		public void mouseExited(MouseEvent e)
		{
			
		}
    }
}
