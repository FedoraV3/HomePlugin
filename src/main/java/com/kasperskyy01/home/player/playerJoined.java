package com.kasperskyy01.home.player;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.kasperskyy01.home.HomePlugin;

public class playerJoined implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        final HomePlugin instance = HomePlugin.getPlugin(HomePlugin.class);
        HashMap<String, Object> toSet = new HashMap<String, Object>(); 
        Player joined = e.getPlayer();
        String uniqueID = joined.getUniqueId().toString();
        String path = "homes." + uniqueID;
        // HashMap addons
        toSet.put(path, null);
        
        // Register player to the YAML File
        instance.YMLMgr.WriteToYAML(joined, toSet);
    }
}
