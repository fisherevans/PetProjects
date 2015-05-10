package info.fisherevans.jcontrol.client;

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
			if(command.startsWith("connect "))
			{
				command = command.replaceAll("^(connect )", "");
				parent.log("Attempting to connect to -> " + command);
				String connection[] = command.split(":");
				parent.connectTo(connection[0], Integer.parseInt(connection[1]));
			}
			else
			{
				if(parent.connected)
				{
					for(int i = 0;i < command.length();i++)
					{
						parent.out.println("press " + command.charAt(i));
						parent.out.println("release " + command.charAt(i));
						parent.out.flush();
					}
				}
				else
				{
					parent.log("Please connect to a server before sending input!");
				}
			}
		}
		}
		catch(IOException e)
		{
			parent.log("There was an error getting user input on the server...");
			e.printStackTrace();
			System.exit(10);
		}
	}
}
