package info.fisherevans.sudosuite.powers;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import info.fisherevans.sudosuite.SudoSuite;

public class Powers
{
	private SudoSuite sudo;
	private ArrayList<Player> godPlayers;

	public Powers(SudoSuite sudo)
	{
		this.sudo = sudo;
		
		godPlayers = new ArrayList<Player>();
	}
	
	public void checkDamage(EntityDamageEvent event)
	{
		if(godPlayers.contains((Player)event.getEntity()))
		{
			event.setCancelled(true);
		}
	}
	
	public void checkHunger(FoodLevelChangeEvent event)
	{
		if(godPlayers.contains((Player)event.getEntity()))
		{
			event.setCancelled(true);
		}
	}
	
	public void commandToggleGod(Player sender)
	{
		if(sudo.pex.hasPerm(sender, "sudosuite.powers.god"))
		{
			if(sender != null)
			{
				toggleGod(sender);
			}
			else
			{
				sudo.noConsolePerm("/god");
			}
		}
		else
		{
			sudo.noPerm(sender, "/god");
		}
	}
	
	public void commandToggleGod(Player sender, Player target)
	{
		if(sudo.pex.hasPerm(sender, "sudosuite.powers.god.other"))
		{
			if(target == null)
			{
				sudo.tellPlayer("That player does not exist.", sender);
			}
			else
			{
				toggleGod(target);
				sudo.tellPlayer("God mode for " + target.getDisplayName() + " is currently: " + godPlayers.contains(target), sender);
			}
		}
		else
		{
			sudo.noPerm(sender, "/god [player]");
		}
	}
	
	public void toggleGod(Player player)
	{
		if(godPlayers.contains(player))
		{
			godPlayers.remove(player);
			sudo.tellPlayer("God mode disabled.", player);
		}
		else
		{
			godPlayers.add(player);
			sudo.tellPlayer("God mode enabled.", player);
		}
		player.setFoodLevel(20);
		player.setHealth(20);
	}
}
