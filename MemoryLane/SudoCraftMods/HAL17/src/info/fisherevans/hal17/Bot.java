package info.fisherevans.hal17;

import java.io.IOException;
import java.util.regex.Pattern;

import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.NickAlreadyInUseException;
import org.jibble.pircbot.PircBot;

import com.google.code.chatterbotapi.*;

public class Bot extends PircBot
{
	String nick = "EveX",
		   server = "holmes.freenode.net",
		   channel = "#sudocraft";

	ChatterBotFactory chatFact;
	ChatterBot eve;
	ChatterBotSession eveSes;
	
	long curTime, lastTime;
	
	public Bot() throws NickAlreadyInUseException, IOException, IrcException
	{
		setName(nick);
		setVerbose(false);
		connect(server);
		joinChannel(channel);

		chatFact = new ChatterBotFactory();
		try
		{ eve = chatFact.create(ChatterBotType.PANDORABOTS, "a9481f8c7e347656"); } catch (Exception e) { }
		
		eveSes = eve.createSession();
		curTime = System.currentTimeMillis();
		lastTime = 0;
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message)
	{
		
		String from = sender;
		String msg = message;
		
		if(Pattern.compile("SudoCraftBot[0-9]{0,1}").matcher(sender).find())
		{
			if(Pattern.compile("\\[[a-zA-Z0-9]+ disconnected\\]").matcher(message).find())
			{
				//say("I hated that guy...");
			}
			else if(Pattern.compile("[a-zA-Z0-9]+ connected").matcher(message).find())
			{
				from = message.replaceAll("^\\[", "").replaceAll(" connected\\]$", "");
				joinMessage(from);
			}
			else
			{
				msg = message.replaceAll("<[a-zA-Z0-9_]+> ", "");
				from = message.replaceAll("^<", "").replaceAll(">.*$", "");
			}
		}
		
		if(Pattern.compile("^(?i)eve:").matcher(msg).find())
		{
			if(true/*lastTime + 1000 < curTime*/)
			{
				lastTime = curTime;
				curTime = System.currentTimeMillis();
				
				msg = msg.replaceAll("^(?i)eve:[ ]*", "");
	
				if(Pattern.compile("^(?i)commands").matcher(msg).find()) // COMMANDS
				{ say("My currently available commands are: about, help, commands, homes, chests, economy, worlds, teleporting, logblock, voting."); }
				
				else if(Pattern.compile("^(?i)about").matcher(msg).find()) // ABOUT
				{ say("I am an IRC bot made and run by Jeebs. My main goal in life is to better people's playing experience in the SudoCraft Minecraft server."); }
				
				else if(Pattern.compile("^(?i)help").matcher(msg).find()) // HELP
				{ say("I'll try to help you out if you use the format 'eve:<command>'. Say 'eve:commands' to view all of my available commands."); }
				
				else if(Pattern.compile("^(?i)homes").matcher(msg).find()) // HOMES
				{ say("To set a home location use '/sethome <name>'. To teleport back to that location, use '/home <same name>'."); }
				
				else if(Pattern.compile("^(?i)chests").matcher(msg).find()) // CHESTS
				{ say("To lock your chests, door, furnaces, etc, use the command '/cprivate' and then punch the item you want to lock. Use '/lwc' to view more commands."); }
				
				else if(Pattern.compile("^(?i)economy").matcher(msg).find()) // ECONOMY
				{ say("You can get to the shop by using '/warp shop' to get to the shop. Right click the 'Buy' and 'Sell' signs to buy and sell blocks and items."); }
				
				else if(Pattern.compile("^(?i)worlds").matcher(msg).find()) // WORLDS
				{ say("Use '/warp portals' to get to the telportation chamber. Walk through the corresponding portals to travel to different worlds. This is also how yo get to PvP and Creative."); }
				
				else if(Pattern.compile("^(?i)teleporting").matcher(msg).find()) // TELPORTING
				{ say("To request that you teleport so someone, use '/tpa <player>'. To request that they teleport to you, use '/tpahere <name>'."); }
				
				else if(Pattern.compile("^(?i)logblock").matcher(msg).find()) // LOGBLOCK
				{ say("To check out block history, use the command '/lb toolblock'. Hit a block to lookup the history of its location. Place the bedrock to look up that location."); }
				
				else if(Pattern.compile("^(?i)voting").matcher(msg).find()) // VOTING
				{ say("Only trusted users and up can participate in voting. Use '/tvote (day/night)' to create a time vote. Use '/wvote (sun/rain)' to create a weather vote."); }
				
				else if(Pattern.compile("^(?i)griefer").matcher(msg).find()) // VOTING
				{ say("Griefing is the act of angering people in video games through the use of destruction, construction, or social engineering. You, my silly friend, are a fuck face."); }
				
				else { try
				{
					String tempMsg = eveSes.think(msg).replaceAll("<br>", "");
					say(tempMsg);
				} catch (Exception e) { } }
			}
		}
		
		if(Pattern.compile("[((?i)like)|((?i)love)].*(?i)turtles").matcher(msg).find())
		{
			say("I FUCKING LOVE TURTLES.");
		}
		
		System.out.println(from + " : " + msg);
	}
	
	public void onPrivateMessage(String sender, String login, String hostname, String message)
	{
		if(sender.equals("jeebs") || sender.equals("summerlilly12"))
		{
			if(message.equals("quit"))
			{
				System.exit(0);
			}
			say(message);
		}
	}
	
	public void onJoin(String channel, String sender, String login, String hostname)
	{
		if(!Pattern.compile(nick).matcher(sender).find())
		{
			joinMessage(sender);
		}
	}
	
	public void joinMessage(String sender)
	{
		String tempMsg = "Welcome to #sudocraft " + sender + "! Say 'eve:help' to see how I can help.";
		say(tempMsg);
	}
	
	public void say(String text)
	{
		sendMessage(channel, text);
	}
	
	public void log(String text)
	{
		System.out.println(text);
	}
}
