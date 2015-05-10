package info.fisherevans.jcontrol.server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config
{
	private Control parent;
	public Properties configFile;
	
	public int port;
	public String password;
	
	public Config(Control parent)
	{
		this.parent = parent;
		
		try
		{
			configFile = new Properties();
			configFile.load(new FileInputStream("JControl.properties"));
			parent.log("File stream successfully opened.");
		}
		catch(IOException e)
		{
			parent.log("There was an error loading the config file");
			e.printStackTrace();
			System.exit(1);
		}

		port = Integer.parseInt(configFile.getProperty("port", "63930"));
		password = configFile.getProperty("password", "pass");

		parent.log("Loaded the port: " + port);
		parent.log("Loaded the password: " + password);

		configFile.setProperty("port", port+"");
		configFile.setProperty("pass", password);
	}
}
