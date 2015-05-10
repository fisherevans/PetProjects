package info.fisherevans.jcontrol.server;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Control
{
	public Config config;
	
	private Robot keyRobot;
	private int keyPressDelay = 20/2;
	
	public Server server;
	private Thread serverThread;
	
	public Commands commands;
	private Thread commandsThread;
	
	public Control()
	{
		log("Starting JControl Server...");
		
		log("Starting command input...");
		commands = new Commands(this);
		commandsThread = new Thread(commands);
		commandsThread.start();
		log("Command polling activated successfully.");
		
		log("Loading configuration from 'JControl.properties'.");
		config = new Config(this);
		log("Configuration successfully loaded.");
		
		try
		{
			keyRobot = new Robot();
		}
		catch(AWTException e)
		{
			log("There was a problem creating the virtual typing agent.");
			log("Quitting now...");
			e.printStackTrace();
			System.exit(1);
		}
		log("Successfully started the virtual typing agent.");

		log("Attempting to start the listening server.");
		server = new Server(this);
		serverThread = new Thread(server);
		serverThread.start();
		log("Successfully started the listening server.");
	}
	
	public void keyPress(int press)
	{
		keyRobot.keyPress(press);
	}
	
	public void keyRelease(int release)
	{
		keyRobot.keyRelease(release);
	}
	
	public void log(String message)
	{
		Calendar calendar = new GregorianCalendar();
		String time = String.format("[%02d:%02d:%02d Server] ",
				calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		System.out.println(time + message);
	}
}
