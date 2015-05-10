package info.fisherevans.sudosuite.chat;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

import info.fisherevans.sudosuite.SudoSuite;

public class Chat
{
	private SudoSuite sudo;
	private List<String> motd, rules, joinMsgs, leaveMsgs, kickMsgs, whoAdmins;
	private String joinPre, leavePre, kickPre, chatFormat, consoleSayFormat, meFormat, whoFirst, whoAdmin, whoNormal, whoNoAdmin, whoNoNormal, shoutFormat, colorsFormat;

	public Chat(SudoSuite sudo)
	{
		this.sudo = sudo;
		
		sudo.print("SubPlugin Initiated", "Chat");

		whoAdmins = sudo.getConfig().getStringList("chat.who.admingroups");
		whoFirst = sudo.getConfig().getString("chat.who.firstline");
		whoAdmin = sudo.getConfig().getString("chat.who.adminline");
		whoNormal = sudo.getConfig().getString("chat.who.normalline");
		whoNoAdmin = sudo.getConfig().getString("chat.who.noadminline");
		whoNoNormal = sudo.getConfig().getString("chat.who.nonormalline");
		
		motd = sudo.getConfig().getStringList("chat.motd");
		rules = sudo.getConfig().getStringList("chat.rules");
		
		joinMsgs = sudo.getConfig().getStringList("chat.join.messages");
		leaveMsgs = sudo.getConfig().getStringList("chat.leave.messages");
		kickMsgs = sudo.getConfig().getStringList("chat.kick.messages");
		joinPre = sudo.getConfig().getString("chat.join.prefix");
		leavePre = sudo.getConfig().getString("chat.leave.prefix");
		kickPre = sudo.getConfig().getString("chat.kick.prefix");

		chatFormat = sudo.getConfig().getString("chat.chatformat");
		consoleSayFormat = sudo.getConfig().getString("chat.consolesayformat");
		shoutFormat = sudo.getConfig().getString("chat.shoutformat");
		meFormat = sudo.getConfig().getString("chat.meformat");
		colorsFormat = sudo.getConfig().getString("chat.colorformat");
	}
	
	public void sendPlayerWho(Player player)
	{
		Player onlinePlayers[] = sudo.getServer().getOnlinePlayers();
		List<Player> admins = new ArrayList<Player>(), normals = new ArrayList<Player>();
		for(Player temp : onlinePlayers)
		{
			if(checkAdmin(sudo.pex.getGroups(temp)))
			{
				admins.add(temp);
			}
			else
			{
				normals.add(temp);
			}
		}
		
		sudo.tellPlayer(whoFirst.replace("+current", onlinePlayers.length + "").replace("+max", sudo.getServer().getMaxPlayers() + ""), player);
		
		String tempMessage;
		
		if(admins.size() < 1)
		{
			sudo.tellPlayer(whoNoAdmin, player);
		}
		else
		{
			sudo.tellPlayer(whoAdmin, player);
			tempMessage = "";
			for(Player admin : admins)
			{
				tempMessage += admin.getDisplayName() + ", ";
			}
			sudo.tellPlayer(tempMessage.replaceAll(", $", ""), player);
		}
		
		if(normals.size() < 1)
		{
			sudo.tellPlayer(whoNoNormal, player);
		}
		else
		{
			sudo.tellPlayer(whoNormal, player);
			tempMessage = "";
			for(Player normal : normals)
			{
				tempMessage += normal.getDisplayName() + ", ";
			}
			sudo.tellPlayer(tempMessage.replaceAll(", $", ""), player);
		}
	}
	
	public void sendPlayerMOTD(Player player)
	{
		for(String line : motd)
		{
			player.sendRawMessage(sudo.formatMsg(line).replace("+player", player.getDisplayName()));
		}
	}
	
	public void sendPlayerRules(Player player)
	{
		for(String line : rules)
		{
			player.sendRawMessage(sudo.formatMsg(line));
		}
	}
	
	public void formatColorsCommand(Player player)
	{
		player.sendRawMessage(sudo.formatMsg(colorsFormat));
	}
	
	public void setJoinMessage(PlayerJoinEvent event)
	{
		String temp = sudo.formatMsg(joinPre + sudo.getRandString(joinMsgs)
				.replace("+player", event.getPlayer().getDisplayName()));
		event.setJoinMessage(temp);
		sudo.print(temp);
	}
	
	public void setQuitMessage(PlayerQuitEvent event)
	{
		String temp = sudo.formatMsg(leavePre + sudo.getRandString(leaveMsgs)
				.replace("+player", event.getPlayer().getDisplayName()));
		event.setQuitMessage(temp);
		sudo.print(temp);
	}
	
	public void setKickMessage(PlayerKickEvent event)
	{
		String temp = sudo.formatMsg(kickPre + sudo.getRandString(kickMsgs)
				.replace("+player", event.getPlayer().getDisplayName())
				.replace("+reason", event.getReason()));
		event.setLeaveMessage(temp);
		sudo.print(temp);
	}
	
	public void setChatMessage(PlayerChatEvent event)
	{
		Player tempP = event.getPlayer();
		String tempS = chatFormat.
				replace("+prefix",sudo.pex.getPrefix(tempP)).
				replace("+suffix",sudo.pex.getSuffix(tempP)).
				replace("+player","%1$s").
				replace("+message","%2$s");
		event.setFormat(sudo.formatMsg(tempS));
	}
	
	public void formatMeCommand(Player player, String message)
	{
		String name = player == null ? "Console" : player.getDisplayName();
		if(sudo.pex.hasPerm(player, "sudosuite.chat.me"))
		{
			sudo.broadcast(meFormat.replace("+player",name).replace("+message", message));
		}
		else
		{
			sudo.noPerm(player, "/me");
		}
	}
	
	public void formatSayCommand(Player player, String message)
	{
		String name = player == null ? "Console" : player.getDisplayName();
		if(sudo.pex.hasPerm(player, "sudosuite.chat.say"))
		{
			if(message.equals(""))
			{
				sudo.alertPlayer("Please use the format '/say message'.", player);
			}
			else
			{
				sudo.broadcast(consoleSayFormat.replace("+message", message));
			}
		}
		else
		{
			sudo.noPerm(player, "/say or /s");
		}
	}
	
	public void formatShoutCommand(Player player, String message)
	{
		if(sudo.pex.hasPerm(player, "sudosuite.chat.shout"))
		{
			if(message.equals(""))
			{
				sudo.alertPlayer("Please use the format '/shout message'.", player);
			}
			else
			{
				sudo.broadcast(shoutFormat.replace("+message", message));
			}
		}
		else
		{
			sudo.noPerm(player, "/shout");
		}
	}
	
	public void formatChatasCommand(Player player, String[] args)
	{
		if(sudo.pex.hasPerm(player, "sudosuite.chat.chatas"))
		{
			Player target = sudo.getPlayer(args[0]);
			
			String message = "";
			for(int loop = 1;loop < args.length;loop++)
			{
				message += args[loop] + " ";
			}
			
			if(sudo.pex.hasPerm(target, "sudosuite.chat.chatas.block") && !sudo.pex.hasPerm(player,"sudosuite.chat.chatas.override"))
			{
				sudo.noPerm(player, "/chatas " + target.getDisplayName());
			}
			else
			{
				String tempS = chatFormat.
						replace("+prefix",sudo.pex.getPrefix(target)).
						replace("+suffix",sudo.pex.getSuffix(target)).
						replace("+player",target.getDisplayName()).
						replace("+message",message);
				sudo.broadcast(tempS);
			}
		}
		else
		{
			sudo.noPerm(player, "/chatas");
		}
	}
	
	public boolean checkAdmin(String[] groups)
	{
		for(String group : groups)
		{
			for(String adminGroup : whoAdmins)
			{
				if(group.equals(adminGroup))
				{
					return true;
				}
			}
		}
		return false;
	}

}
