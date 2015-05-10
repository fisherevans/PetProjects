package info.fisherevans.sudosuite;

import info.fisherevans.sudosuite.chat.Chat;
import info.fisherevans.sudosuite.powers.Powers;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SudoSuite extends JavaPlugin
{
	private Logger console = Logger.getLogger("Minecraft");
	private String pVersion, pName;
	
	private PlayerListener listener;
	private CommandListener commandListener;
	
	public Permissions pex;
	public Chat pChat;
	public Powers pPowers;
	
	public PluginManager pm;
	
	public void onEnable()
	{
		pm = this.getServer().getPluginManager();
		pVersion = this.getDescription().getVersion();
		pName = this.getDescription().getName();
		print("Plugin Initiated");
		
		pex = new Permissions(this);
		
		if(!pex.getPex())
		{
			print("SudoSuite requires PermissionsEx to run.");
			onDisable();
		}
		else
		{
			listener = new PlayerListener(this);
			pm.registerEvents(listener, this);
			print("Player Listener Registered");
			
			loadConfig();
			
			commandListener = new CommandListener(this);

			if(getConfig().getBoolean("subplugins.chat"))
			{
				getCommand("me").setExecutor(commandListener);
				getCommand("s").setExecutor(commandListener);
				getCommand("say").setExecutor(commandListener);
				getCommand("motd").setExecutor(commandListener);
				getCommand("rules").setExecutor(commandListener);
				getCommand("chatas").setExecutor(commandListener);
			}	
			
			if(getConfig().getBoolean("subplugins.powers"))
			{
				getCommand("god").setExecutor(commandListener);
			}	
		}
	}
	
	public void loadConfig()
	{
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		if(getConfig().getBoolean("subplugins.chat"))
		{ pChat = new Chat(this); }
		
		if(getConfig().getBoolean("subplugins.powers"))
		{ pPowers = new Powers(this); }
	}
	
	public void onDisable()
	{
		print("Plugin closing.");
	}
	
	public void noPerm(Player player, String command)
	{
		tellPlayer(formatMsg(getConfig().getString("sudosuite.errorplayerformat").
				replace("+player", player.getDisplayName()).
				replace("+command", command))
		,player );
		
		print(getConfig().getString("sudosuite.errorconsoleformat").
				replace("+player", player.getDisplayName()).
				replace("+command", command)
		);
	}

	public void noPerm(String player, String command)
	{
		noPerm(getServer().getPlayer(player), command);
	}
	
	public void noConsolePerm(String command)
	{
		print("The Console cannot use the " + command + " command.");
	}
	
	public String formatMsg(String message)
	{
		return message.replace("~", "§");
	}
	
	public Player getPlayer(String ref)
	{
		for(Player temp : getServer().getOnlinePlayers())
		{
			if(temp.getDisplayName().toLowerCase().contains(ref.toLowerCase()))
			{
				return temp;
			}
		}
		return null;
	}
	
	public String getRandString(List<String> list)
	{
		return list.get((int)(Math.random()*list.size()));
	}
	
	public void broadcast(String message)
	{
		this.getServer().broadcastMessage(formatMsg(message));
	}
	
	public void broadcastRank(String message, String rank)
	{
		this.getServer().broadcast(formatMsg(message), rank);
	}
	
	public void tellPlayer(String message, String player)
	{
		tellPlayer(message, getServer().getPlayer(player));
	}
	
	public void tellPlayer(String message, Player player)
	{
		if(player != null)
		{
			player.sendRawMessage(formatMsg(message));
		}
		else
		{
			print(message);
		}
	}
	
	public void print(String message)
	{
		print(message, "Core");
	}
	
	public void print(String message, String pluginName)
	{
		console.info(getConfig().getString("sudosuite.consoleformat")
			.replace("+plugin", pName)
			.replace("+version", pVersion)
			.replace("+subplugin", pluginName)
			.replace("+message", message.replaceAll("§[0-9a-fA-F]", ""))
		);
	}
}
