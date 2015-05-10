package info.fisherevans.jcontrol.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Control
{
	public Commands commands;
	private Thread commandsThread;
	
	public boolean connected = false;
	
	public Socket clientSocket;
	public PrintWriter out;
	
	public Control()
	{
		log("Starting JControl Client...");
		
		log("Starting command input...");
		commands = new Commands(this);
		commandsThread = new Thread(commands);
		commandsThread.start();
		log("Command polling activated successfully.");
	}
	
	public void connectTo(String ip, int port)
	{
		if(!connected)
		{
			try
			{
				clientSocket = new Socket(ip, port);
				out = new PrintWriter(clientSocket.getOutputStream(), true);
				out.println("ready");
				out.flush();
				connected = true;
			}
			catch (UnknownHostException e)
			{
				log("Unknown host: " + ip);
			}
			catch (IOException e)
			{
				log("There was a problem creating the socket.");
			}
		}
		else
		{
			log("You are already connected to a server.");
		}
	}
	
	public void log(String message)
	{
		Calendar calendar = new GregorianCalendar();
		String time = String.format("[%02d:%02d:%02d Client] ",
				calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
		System.out.println(time + message);
	}
}
