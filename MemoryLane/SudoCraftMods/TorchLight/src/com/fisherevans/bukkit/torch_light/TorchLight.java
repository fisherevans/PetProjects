package com.fisherevans.bukkit.torch_light;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

/**
 * Author: Fisher Evans
 * Date: 1/6/14
 */
public class TorchLight extends JavaPlugin implements Listener {
    public static final int MAX_AIR_SHIFT = 1;

    private HashMap<Player, PlayerLight> _lightMap;

    @Override
    public void onLoad() {
        _lightMap = new HashMap<>();
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        PlayerMoveEvent.getHandlerList().unregister((JavaPlugin)this);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        if(!_lightMap.keySet().contains(p)) {
            _lightMap.put(p, new PlayerLight());
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        if(_lightMap.containsKey(p)) {
            _lightMap.get(p).move(event);
        }
    }
}
