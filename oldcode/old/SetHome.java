package com.kasperskyy01.home.old;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.kasperskyy01.home.HomePlugin;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;

public class SetHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command _u1, String _u2, String[] args) {
        if (sender instanceof Player && args.length > 0) {
            final HomePlugin instance = HomePlugin.getPlugin(HomePlugin.class);
            Player player = Bukkit.getPlayer(sender.getName());

            FileConfiguration homes = instance.getHomeYML();
            String uuid = player.getUniqueId().toString();

            ConfigurationSection playerHomes = homes.getConfigurationSection("homes." + uuid);
            String _t = "homes." + uuid + "." + args[0];

            if (playerHomes == null) {
                instance.getLogger().info("Player has no homes");
            } else {
                int home = playerHomes.getKeys(false).size();
                if (home > instance.getConfig().getInt("home-limit") - 1 && playerHomes != null) {
                    player.sendMessage("You have the maximum limit of homes!");
                    return true;
                }
            }

            Location location = player.getLocation();

            instance.scheduler.runTaskAsynchronously(instance, () -> {

                homes.set(_t + ".world", location.getWorld().getName());
                homes.set(_t + ".x", location.getX());
                homes.set(_t + ".y", location.getY());
                homes.set(_t + ".z", location.getZ());
                        
                try {
                    File file = new File(instance.getDataFolder(), "homes.yml");
                    homes.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });

            player.sendMessage("Added your home " + args[0]);
            return true;
        } else {
            return false;
        }
    }
}
