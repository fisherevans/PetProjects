package info.fisherevans.sudosuite.powers;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.util.Vector;

import info.fisherevans.sudosuite.SudoSuite;
import info.fisherevans.sudosuite.powers.slap.SlapTask;

public class Powers
{
	private SudoSuite sudo;
	
	private double slappower, maxslaps, slapInterval;
	private String slapMessageFormat;
	
	public HashMap<Player,String> lastCommands;

	public Powers(SudoSuite sudo)
	{
		this.sudo = sudo;

		slapInterval = sudo.getConfig().getInt("powers.slap.interval");
		slappower = sudo.getConfig().getInt("powers.slap.power");
		maxslaps = sudo.getConfig().getInt("powers.slap.max");
		slapMessageFormat = sudo.getConfig().getString("powers.slap.message");
		
		lastCommands = new HashMap<Player,String>();
	}
	
	public void commandLast(Player player)
	{
		if(lastCommands.containsKey(player))
		{
			player.chat("/" + lastCommands.get(player));
		}
		else
		{
			sudo.alertPlayer("There is no command for you to repeat!", player);
		}
	}
	
	public void commandSlap(Player player, Player target, int amount)
	{
		if(sudo.pex.hasPerm(player, "sudosuite.powers.slap"))
		{
			if(target == null)
			{
				sudo.alertPlayer("That doesn't seem to be a real player.", player);
			}
			else if(sudo.pex.hasPerm(target, "sudosuite.powers.slap.block") && !sudo.pex.hasPerm(player, "sudosuite.powers.slap.override"))
			{
				sudo.noPerm(player, "/slap " + target.getDisplayName());
			}
			else if(amount < 1)
			{
				sudo.alertPlayer("Please user a number greater than 0.", player);
			}
			else
			{
				if(amount > maxslaps)
				{
					amount = (int) maxslaps;
					sudo.alertPlayer("That's too many slaps. Defaulting to " + maxslaps + ".", player);
				}
				sudo.notifyPlayer(target.getDisplayName() + " is now being slapped " + amount + " times.", player);
				sudo.alertPlayer(slapMessageFormat.replace("+player", player.getDisplayName()).replace("+amount", amount + ""), target);
				if(amount == 1)
				{
					slapPlayer(target);
				}
				else
				{
					SlapTask tempTask = new SlapTask(amount, target, this);
					tempTask.setId(sudo.getServer().getScheduler().scheduleAsyncRepeatingTask(sudo, tempTask, 0L, (long) (slapInterval)));
				}
			}
		}
		else
		{
			sudo.noPerm(player, "/slap");
		}
	}
	
	public void slapPlayer(Player player)
	{
		int hp = player.getHealth();
		if(hp == 1)
		{
			player.setHealth(hp + 1);
		}
		player.damage(1);
		player.setHealth(hp);
		player.setVelocity(new Vector((Math.random()*slappower)-(slappower/2), slappower, (Math.random()*slappower)-(slappower/2)));
	}
	
	public SudoSuite getSudo()
	{
		return sudo;
	}
}
