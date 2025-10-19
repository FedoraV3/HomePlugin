package com.kasperskyy01.home.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
        File file = new File(instance.getDataFolder(), "homes.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(file);

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
}
