package info.fisherevans.jcontrol.server;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable
{
	Control parent;
	
	ServerSocket serverSocket;
	Socket clientSocket;
	
	PrintWriter out;
	BufferedReader in;
	
	public boolean running = true;
	
	public Server(Control parent)
	{
		this.parent = parent;
		try
		{
			serverSocket = new ServerSocket(parent.config.port);
		}
		catch(Exception e)
		{
			parent.log("There was an error starting the server.");
			parent.log("Is the port " + parent.config.port + " in use?");
			System.exit(2);
		}
	}
	
	public void run()
	{
		parent.log("Awaiting a client connection");
		try
		{
			clientSocket = serverSocket.accept();
		}
		catch(IOException e)
		{
			parent.log("There was an error accepting the client connection.");
			e.printStackTrace();
			System.exit(3);
		}
		parent.log("A client has connected from: " + clientSocket.getRemoteSocketAddress());
		
		try
		{
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			
			int timeout = 0;
			while(!in.ready())
			{
				Thread.sleep(25);
				timeout++;
				if(timeout > 200)
				{
					parent.log("the client is taking too long to send a state code.");
					System.exit(6);
				}
			}
			
			String input = in.readLine();
			if(!input.equals("ready"))
			{
				parent.log("The client was not ready to accept a new connection.");
				System.exit(5);
			}
		
			out.print(1);
			out.flush();
		}
		catch(IOException e)
		{
			parent.log("There was an error establishing a connection between the client and server.");
			e.printStackTrace();
			System.exit(4);
		}
		catch (InterruptedException e)
		{
			parent.log("The server thread was interupted");
			e.printStackTrace();
		}
		parent.log("Connection established to the new client.");
		
		parent.log("Currently polling for user input.");
		try
		{
			String input;
			while(running)
			{
				if(clientSocket.isClosed())
				{
					parent.log("Client has closed the connection. Quitting...");
					break;
				}
				Thread.sleep(20);
				if((input = in.readLine()) != null)
				{
					parent.log("Got command from client -> " + input);
					if(input.startsWith("press "))
					{
						input = input.replaceAll("^press ", "");
						parent.keyPress(getKey(input));
					}
					if(input.startsWith("release "))
					{
						input = input.replaceAll("^release ", "");
						parent.keyRelease(getKey(input));
					}
				}
			}
			in.close();
			out.close();
			clientSocket.close();
			serverSocket.close();
		}
		catch(IOException e)
		{
			parent.log("There was an error reading user input...");
			e.printStackTrace();
			System.exit(8);
		}
		catch (InterruptedException e)
		{
			parent.log("The server thread was interupted");
			e.printStackTrace();
		}
	}
	
	public int getKey(String input)
	{
		int key = KeyEvent.VK_ENTER;
		switch(input.charAt(0))
		{
	        case 'a': key = KeyEvent.VK_A; break;
	        case 'b': key = KeyEvent.VK_B; break;
	        case 'c': key = KeyEvent.VK_C; break;
	        case 'd': key = KeyEvent.VK_D; break;
	        case 'e': key = KeyEvent.VK_E; break;
	        case 'f': key = KeyEvent.VK_F; break;
	        case 'g': key = KeyEvent.VK_G; break;
	        case 'h': key = KeyEvent.VK_H; break;
	        case 'i': key = KeyEvent.VK_I; break;
	        case 'j': key = KeyEvent.VK_J; break;
	        case 'k': key = KeyEvent.VK_K; break;
	        case 'l': key = KeyEvent.VK_L; break;
	        case 'm': key = KeyEvent.VK_M; break;
	        case 'n': key = KeyEvent.VK_N; break;
	        case 'o': key = KeyEvent.VK_O; break;
	        case 'p': key = KeyEvent.VK_P; break;
	        case 'q': key = KeyEvent.VK_Q; break;
	        case 'r': key = KeyEvent.VK_R; break;
	        case 's': key = KeyEvent.VK_S; break;
	        case 't': key = KeyEvent.VK_T; break;
	        case 'u': key = KeyEvent.VK_U; break;
	        case 'v': key = KeyEvent.VK_V; break;
	        case 'w': key = KeyEvent.VK_W; break;
	        case 'x': key = KeyEvent.VK_X; break;
	        case 'y': key = KeyEvent.VK_Y; break;
	        case 'z': key = KeyEvent.VK_Z; break;
	        case '0': key = KeyEvent.VK_0; break;
	        case '1': key = KeyEvent.VK_1; break;
	        case '2': key = KeyEvent.VK_2; break;
	        case '3': key = KeyEvent.VK_3; break;
	        case '4': key = KeyEvent.VK_4; break;
	        case '5': key = KeyEvent.VK_5; break;
	        case '6': key = KeyEvent.VK_6; break;
	        case '7': key = KeyEvent.VK_7; break;
	        case '8': key = KeyEvent.VK_8; break;
	        case '9': key = KeyEvent.VK_9; break;
	        case ' ': key = KeyEvent.VK_SPACE; break;
		}
		parent.log("Got the key code: " + key + "  - for the key: " + input.charAt(0));
		return key;
	}
}
