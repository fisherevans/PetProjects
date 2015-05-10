package info.fisherevans.sudosuite;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class PlayerListener implements Listener
{
	private SudoSuite sudo;
	
	public PlayerListener(SudoSuite sudo)
	{
		this.sudo = sudo;
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onJoin(PlayerJoinEvent event)
	{
		if(sudo.getConfig().getBoolean("subplugins.chat"))
		{
			sudo.pChat.sendPlayerMOTD(event.getPlayer());
			sudo.pChat.setJoinMessage(event);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onQuit(PlayerQuitEvent event)
	{
		if(sudo.getConfig().getBoolean("subplugins.chat"))
		{
			sudo.pChat.setQuitMessage(event);
		}
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onKick(PlayerKickEvent event)
	{
		if(sudo.getConfig().getBoolean("subplugins.chat"))
		{
			sudo.pChat.setKickMessage(event);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(PlayerChatEvent event)
	{
		event.setMessage(sudo.formatMsg(event.getMessage()));
		if(sudo.getConfig().getBoolean("subplugins.chat"))
		{
			sudo.pChat.setChatMessage(event);
		}
	}
}
