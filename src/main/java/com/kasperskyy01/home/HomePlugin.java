package com.kasperskyy01.home;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.kasperskyy01.home.file.YAMLManager;

public class HomePlugin extends JavaPlugin {
    BukkitScheduler scheduler = Bukkit.getScheduler();
    private File customConfigFile;
    private FileConfiguration customConfig;
    
    public YAMLManager YMLMgr;
    
    public FileConfiguration getHomeYML() {
        return this.customConfig;
    }

    public void HomeYML() {
        customConfigFile = new File(getDataFolder(), "homes.yml");

        if (customConfigFile.exists()) {
            getLogger().info("Found homes.yml file");
            customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        } else {
            getDataFolder().mkdirs();
            try {
                if (customConfigFile.createNewFile()) {
                    getLogger().info("Created new homes.yml file");
                }
            } catch (IOException e) {
                getLogger().severe("Could not create homes.yml!");
                e.printStackTrace();
            }
            customConfig = YamlConfiguration.loadConfiguration(customConfigFile);
        }
    }


    @Override
    public void onEnable() {
        getLogger().info("HomePlugin has been enabled!");


        // initizihaizlizetion of files
        HomeYML();

        // Class initizihaizlizetion
        this.YMLMgr = new YAMLManager();

        // initizihaizlizetion of commands
        this.getCommand("sethome").setExecutor(new NSetHome());

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("HomePlugin has been disabled!");
        

        // deinit
        this.getCommand("sethome").setExecutor(null);
        this.getCommand("home").setExecutor(null);
        this.getCommand("delhome").setExecutor(null);
    }
    
}
