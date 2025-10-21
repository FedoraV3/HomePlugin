package com.kasperskyy01.home.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    // TODO: Make this a dynamic function so that it works for other stuff too
    public List<String> getLocation(String path) {
        final HomePlugin instance = HomePlugin.getPlugin(HomePlugin.class);
        FileConfiguration YAML = instance.getHomeYML();
        List<String> result = new ArrayList<>();


        // Get the YAML Content
        ConfigurationSection data = YAML.getConfigurationSection(path);

        // Get values
        result.add((String) data.get(path + ".world"));
        result.add((String) data.get(path + ".x"));
        result.add((String) data.get(path + ".y"));
        result.add((String) data.get(path + ".z"));       

        // TODO: Fix this function
        return result;
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
