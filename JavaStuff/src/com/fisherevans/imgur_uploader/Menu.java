package info.fisherevans.imguruploader;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.apache.commons.codec.binary.Base64;

public class Menu
{
	private TrayIcon trayIcon;
	private SystemTray sysTray;
	private Image trayImage, trayImageLoading;
	private MenuListener menuListener;
	private PopupMenu popupMenu;
	
	private Font normalFont, boldFont;
	private MenuItem itemExit, itemAbout, itemUpload;
	
	private int popupConfirm;
	
	private Clipboard clipboard;
	
	public Menu()
	{
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		if(SystemTray.isSupported())
		{
			sysTray = SystemTray.getSystemTray();
			trayImage = Toolkit.getDefaultToolkit().getImage("src/images/iu.png");
			trayImageLoading = Toolkit.getDefaultToolkit().getImage("src/images/load.gif");
			menuListener = new MenuListener();
			popupMenu = new PopupMenu();

			normalFont = new Font("Arial", Font.PLAIN, 12);
			boldFont = new Font("Arial", Font.BOLD, 12);

			itemUpload = new MenuItem("Upload Clipboard");
			itemAbout = new MenuItem("About");
			itemExit = new MenuItem("Exit");

			itemUpload.addActionListener(menuListener);
			itemAbout.addActionListener(menuListener);
			itemExit.addActionListener(menuListener);

			itemUpload.setFont(boldFont);
			itemAbout.setFont(normalFont);
			itemExit.setFont(normalFont);

			popupMenu.add(itemUpload);
			popupMenu.add(itemAbout);
			popupMenu.add(itemExit);
			
			trayIcon = new TrayIcon(trayImage, "Imgur Desktop Uploader", popupMenu);
			
			trayIcon.addActionListener(menuListener);
			
			try
			{
				sysTray.add(trayIcon);
			} catch(Exception e) {
				
			}
			
			
		}
		else // Tray not supported
		{
			
		}
	}
	
	private void uploadImage(BufferedImage imageToUp)
	{
		// Uploading tooltip + upload icon
		trayIcon.displayMessage("Upload Starting", "Please wait while your image is uploaded to Imgur.com...", MessageType.INFO);
		trayIcon.setImage(trayImageLoading);

		try
		{
			// Creates Byte Array from picture
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ImageIO.write(imageToUp, "png", byteStream);

			//Encode picture to Base64, and make url
			URL imgurUrl = new URL("http://api.imgur.com/2/upload");

			String image64 = new String(Base64.encodeBase64(byteStream.toByteArray()));
			String uploadKey = URLEncoder.encode("edit", "UTF-8") + "&" + URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(new String(Base64.encodeBase64(byteStream.toByteArray())), "UTF-8");
			uploadKey += "&" + URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode("50484439569ebf3bb168cb6d9fe14b06", "UTF-8");

			// Open connection and send image with api key
			URLConnection httpConn = imgurUrl.openConnection();
			httpConn.setDoOutput(true);
			OutputStreamWriter httpWriter = new OutputStreamWriter(httpConn.getOutputStream());
			httpWriter.write(uploadKey);
			httpWriter.flush();
			
			// Get the resulting url
			BufferedReader httpReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String httpReadBuffer;
			String httpReadContent = "";
			while((httpReadBuffer = httpReader.readLine()) != null)
				httpReadContent += httpReadBuffer;
			
			// Get the image hash and put url in clipboard
			System.out.print(httpReadContent);
			String finalURL = httpReadContent.replaceAll(".+<hash>", "").replaceAll("</hash>.+", "");
			StringSelection clipboardBuffer = new StringSelection("http://www.imgur.com/" + finalURL);
			clipboard.setContents(clipboardBuffer, null);
			
			// Back to normal icon, tell them it's done uploading
			trayIcon.setImage(trayImage);
			trayIcon.displayMessage("Complete", "You image has finished uploading. The imgur URL is in your clipboard.", MessageType.INFO);
		} catch(Exception e) {
			// There was an error uploading the image, with the clipboard, etc... Tell them, and set icon back.
			trayIcon.setImage(trayImage);
			trayIcon.displayMessage("Imgur Upload Error", "There was a fatal error haulting the Imgur upload process. Please try again.\n" + e.toString(), MessageType.ERROR);
			e.printStackTrace();
		}
	}
	
	public void cropImage(BufferedImage image)
	{
		Crop crop = new Crop(image);
	}
	
	public class MenuListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent event)
		{
			Object src = event.getSource();
			
			if(src == trayIcon || src == itemUpload) // default action - upload clipboard
			{
				try
				{
					cropImage((BufferedImage) clipboard.getData(DataFlavor.imageFlavor));
				} catch(Exception e) {
					trayIcon.displayMessage("No Image", "There is no image in your clipboard.", MessageType.WARNING);
				}
			}
			else if(src == itemAbout) // display about page.
			{
				try
				{
					Desktop.getDesktop().browse(URI.create("http://www.fisherevans.info/"));
				} catch(Exception e) {
					trayIcon.displayMessage("Internal Error", "There ws a problem opening the about webpage...", MessageType.ERROR);
				}
			}
			else if(src == itemExit) // exit the program.
			{
				System.exit(0);
			}
		}
	}
}
