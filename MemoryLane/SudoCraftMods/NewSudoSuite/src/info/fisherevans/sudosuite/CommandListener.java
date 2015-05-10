package info.fisherevans.sudosuite;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListener implements CommandExecutor
{
	private SudoSuite sudo;
	
	public CommandListener(SudoSuite sudo)
	{
		this.sudo = sudo;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = null;
		if (sender instanceof Player)
		{
			player = (Player) sender;
		}
		
		if(!command.getName().equals("."))
		{
			sudo.pPowers.lastCommands.put(player, command.getName() + " " + command.getName().equals("say"));
		}
		
		String temp = player == null ? "Console" : player.getDisplayName();
		String subPlugin = "Core";
		
		if(sudo.getConfig().getBoolean("subplugins.chat")) // CHAT
		{
			if(command.getName().equals("me"))
			{
				sudo.pChat.formatMeCommand(player, StringUtils.join(args, " "));
				subPlugin = "Chat";
			}
			else if(command.getName().equals("s") || command.getName().equals("say"))
			{
				sudo.pChat.formatSayCommand(player, StringUtils.join(args, " "));
				subPlugin = "Chat";
			}
			else if(command.getName().equals("shout"))
			{
				sudo.pChat.formatShoutCommand(player, StringUtils.join(args, " "));
				subPlugin = "Chat";
			}
			else if(command.getName().equals("colors"))
			{
				sudo.pChat.formatColorsCommand(player);
				subPlugin = "Chat";
			}
			else if(command.getName().equals("who") || command.getName().equals("list"))
			{
				sudo.pChat.sendPlayerWho(player);
				subPlugin = "Chat";
			}
			else if(command.getName().equals("motd"))
			{
				if(player == null)
				{
					sudo.noConsolePerm("motd");
				}
				else
				{
					sudo.pChat.sendPlayerMOTD(player);
				}
				subPlugin = "Chat";
			}
			else if(command.getName().equals("rules"))
			{
				if(player == null)
				{
					sudo.noConsolePerm("rules");
				}
				else
				{
					sudo.pChat.sendPlayerRules(player);
				}
				subPlugin = "Chat";
			}
			else if(command.getName().equals("chatas"))
			{
				sudo.pChat.formatChatasCommand(player, args);
				subPlugin = "Chat";
			}
		}
		
		if(sudo.getConfig().getBoolean("subplugins.powers")) // POWERS
		{
			if(command.getName().equals("slap"))
			{
				if(args.length > 1)
				{
					sudo.pPowers.commandSlap(player, sudo.getPlayer(args[0]), Integer.parseInt(args[1]));
				}
				else if(args.length > 0)
				{
					sudo.pPowers.commandSlap(player, sudo.getPlayer(args[0]), 1);
				}
				else
				{
					sudo.pPowers.commandSlap(null, null, 0);
				}
				subPlugin = "Powers";
			}
			else if(command.getName().equals("."))
			{
				if(args.length > 1)
				{
					sudo.pPowers.commandSlap(player, sudo.getPlayer(args[0]), Integer.parseInt(args[1]));
				}
				else if(args.length > 0)
				{
					sudo.pPowers.commandSlap(player, sudo.getPlayer(args[0]), 1);
				}
				else
				{
					sudo.pPowers.commandSlap(null, null, 0);
				}
				subPlugin = "Powers";
			}
		}
		sudo.print(temp + " issued the command: " + command.getName() + " " + StringUtils.join(args, " "), subPlugin);
		return true;
	}

}
