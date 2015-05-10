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
		
		String temp = player == null ? "Console" : player.getDisplayName();
		sudo.print(temp + " issued the command: " + command.getName() + " " + StringUtils.join(args, " "));
		
		if(sudo.getConfig().getBoolean("subplugins.chat")) // CHAT
		{
			if(command.getName().equals("me"))
			{
				sudo.pChat.formatMeCommand(player, StringUtils.join(args, " "));
			}
			else if(command.getName().equals("s") || command.getName().equals("say"))
			{
				sudo.pChat.formatSayCommand(player, StringUtils.join(args, " "));
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
			}
			else if(command.getName().equals("chatas"))
			{
				sudo.pChat.formatChatasCommand(player, args);
			}
		}
		
		if(sudo.getConfig().getBoolean("subplugins.powers")) // POWERS
		{
			if(command.getName().equals("god"))
			{
				if(args.length > 0)
				{
					sudo.pPowers.commandToggleGod(player, sudo.getPlayer(args[0]));
				}
				else
				{
					sudo.pPowers.commandToggleGod(player);
				}
			}
		}
		return true;
	}

}
