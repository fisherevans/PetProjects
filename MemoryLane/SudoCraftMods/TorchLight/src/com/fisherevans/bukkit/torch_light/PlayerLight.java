package com.fisherevans.bukkit.torch_light;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

/**
 * Author: Fisher Evans
 * Date: 1/6/14
 */
public class PlayerLight {
    private Location _currentLocation;

    public void move(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        Location newLightLocation = getLightLocation(p);
        if(!newLightLocation.equals(_currentLocation)) {
            if(_currentLocation != null) {
                Block realBlock = _currentLocation.getBlock();
                p.sendBlockChange(_currentLocation, realBlock.getType(), realBlock.getData());
            }
            _currentLocation = newLightLocation;
            p.sendBlockChange(_currentLocation, Material.GLOWSTONE, (byte)0);
        }
    }

    public static Location getLightLocation(Player p) {
        Location playerLocation = p.getLocation(), lightLocation = p.getLocation();
        if(playerLocation.getPitch() < 0) { // looking up
            lightLocation = lightLocation.subtract(0, 1, 0);
            for(int airShift = 0;!lightLocation.getBlock().getType().isSolid() && airShift < TorchLight.MAX_AIR_SHIFT;airShift++) {
                lightLocation = lightLocation.subtract(0, 1, 0);
            }
        } else { // looking down
            lightLocation = lightLocation.add(0, 2, 0);
            for(int airShift = 0;!lightLocation.getBlock().getType().isSolid() && airShift < TorchLight.MAX_AIR_SHIFT;airShift++) {
                lightLocation = lightLocation.add(0, 1, 0);
            }
        }
        return lightLocation;
    }
}
