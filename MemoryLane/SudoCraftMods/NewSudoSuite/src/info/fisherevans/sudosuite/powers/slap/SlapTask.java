package info.fisherevans.sudosuite.powers.slap;

import info.fisherevans.sudosuite.powers.Powers;

import org.bukkit.entity.Player;

public class SlapTask implements Runnable
{
	private int id, amount, track;;
	private Player player;
	private Powers master;
	
	public SlapTask(int amount, Player player, Powers master)
	{
		id = 0;
		this.amount = amount;
		this.player = player;
		this.master = master;
		track = 0;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void run() 
    {
        master.slapPlayer(player);
        track++;
        
        if(track >= amount)
        {
        	master.getSudo().getServer().getScheduler().cancelTask(id);
        }
    }
}
