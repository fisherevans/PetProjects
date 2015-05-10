package info.fisherevans.sudosuite;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Permissions
{
	private SudoSuite sudo;
	private PermissionManager pex;
	
	public Permissions(SudoSuite sudo)
	{
		this.sudo = sudo;
	}
	
	public boolean getPex()
	{
		if(!sudo.getServer().getPluginManager().isPluginEnabled("PermissionsEx"))
		{ return false; }
		
		pex = PermissionsEx.getPermissionManager();
		
		return true;
	}

	public boolean hasPerm(Player player, String node)
	{
		if(player == null)
		{
			return true;
		}
		return pex.has(player,node);
	}

	public boolean hasPerm(String player, String node)
	{
		if(player == null || player == "Console" || player == "")
		{
			return true;
		}	
		return pex.has(sudo.getServer().getPlayer(player),node);
	}

	public String getPrefix(Player player)
	{
		return pex.getUser(player).getPrefix();
	}
	
	public String getPrefix(String player)
	{
		return pex.getUser(sudo.getServer().getPlayer(player)).getPrefix();
	}
	
	public String getSuffix(Player player)
	{
		return pex.getUser(player).getSuffix();
	}
	
	public String getSuffix(String player)
	{
		return pex.getUser(sudo.getServer().getPlayer(player)).getSuffix();
	}
}
