package info.fisherevans.sudosuite.chat;

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
	private List<String> motd, rules, joinMsgs, leaveMsgs, kickMsgs;
	private String joinPre, leavePre, kickPre, chatFormat, consoleSayFormat, meFormat;

	public Chat(SudoSuite sudo)
	{
		this.sudo = sudo;
		
		sudo.print("SubPlugin Initiated", "Chat");

		motd = sudo.getConfig().getStringList("chat.motd");
		rules = sudo.getConfig().getStringList("chat.rules");
		
		joinMsgs = sudo.getConfig().getStringList("chat.joinmessages");
		leaveMsgs = sudo.getConfig().getStringList("chat.leavemessages");
		kickMsgs = sudo.getConfig().getStringList("chat.kickmessages");
		joinPre = sudo.getConfig().getString("chat.joinprefix");
		leavePre = sudo.getConfig().getString("chat.leaveprefix");
		kickPre = sudo.getConfig().getString("chat.kickprefix");

		chatFormat = sudo.getConfig().getString("chat.chatformat");
		consoleSayFormat = sudo.getConfig().getString("chat.consolesayformat");
		meFormat = sudo.getConfig().getString("chat.meformat");
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
			sudo.broadcast(consoleSayFormat.replace("+message", message));
		}
		else
		{
			sudo.noPerm(player, "/say or /s");
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

}
