package com.kasperskyy01.home.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import com.kasperskyy01.home.HomePlugin;


// This file handles YAML Operations
public class YAMLManager {
    @SuppressWarnings("null")
    public void WriteToYAML(Player player, HashMap<String, Object> list) {

        // Find the YAML File
        final HomePlugin instance = HomePlugin.getPlugin(HomePlugin.class);

        // Scheduler Stuff
        BukkitScheduler scheduler = Bukkit.getScheduler();
 
        // Grab YML File
        FileConfiguration data = instance.getHomeYML();

        // TODO: Move this somewhere so other modules can use this function
        // Check for home limit
        // int home = data.getKeys(false).size();
        // if (home > instance.getConfig().getInt("home-limit") - 1 && data != null) {
        //     player.sendMessage("You have the maximum limit of homes!");
        //     return;
        // }

        // Iterate over the list to set
        for (String i : list.keySet()) {
            String path = i;
            Object dataYAML = list.get(i);
            data.set(path, dataYAML);
        }

        // File operations
        scheduler.runTaskAsynchronously(instance, () -> {
            try {
                File homeymlfile = new File(instance.getDataFolder(), "homes.yml");
                data.save(homeymlfile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Done
    
    }

    public boolean playerAtHomeLimit(Player player) {

        final HomePlugin instance = HomePlugin.getPlugin(HomePlugin.class);
        String uuid = player.getUniqueId().toString();

        FileConfiguration data = instance.getHomeYML();
        ConfigurationSection plrHomes = data.getConfigurationSection("homes." + uuid);
        if (plrHomes == null) { return false; }
        Set<String> Data = plrHomes.getKeys(false); 

        if (Data.isEmpty()) {
            return false;
        } else {
            int amountOfHomes = Data.size();
            if (amountOfHomes > instance.getConfig().getInt("home-limit") - 1) {
                return true;
            } else {
                return false;
            }
        }
        
    }
}
