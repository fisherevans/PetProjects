package info.fisherevans.jcontrol.server;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Commands implements Runnable
{
	Control parent;
	public Commands(Control parent)
	{
		this.parent = parent;
	}
	
	public void run()
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String command;
		
		try
		{
		while(true)
		{
			command = input.readLine();
			if(command.equals("quit"))
			{
				parent.log("Quit command issued...");
				parent.log("Saving the current properties.");
				parent.config.configFile.store(new FileOutputStream("JControl.properties"), null);
				parent.log("Turning off the listening server.");
				parent.server.running = false;
				parent.log("Exiting.");
				System.exit(0);
			}
			else
			{
				parent.log("[ERROR] " + command + " is not a recognised command.");
			}
		}
		}
		catch(IOException e)
		{
			parent.log("There was an error getting user input ont he server...");
			e.printStackTrace();
			System.exit(10);
		}
	}
}
