package com.kasperskyy01.home.old;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.kasperskyy01.home.HomePlugin;

// "homes." + uuid + "." + _t

public class DelHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command _t1, String _t2, String[] args) {
        if (sender instanceof Player && args.length > 0) {
            final HomePlugin instance = HomePlugin.getPlugin(HomePlugin.class);

            Player player = Bukkit.getPlayer(sender.getName());
            String uuid = player.getUniqueId().toString();

            FileConfiguration homes = instance.getHomeYML();
            ConfigurationSection playerHomes = homes.getConfigurationSection("homes." + uuid);
            var keys = playerHomes.getKeys(false);


            for (String i : keys) {
                if (i.equalsIgnoreCase(args[0])) {
                    
                    if (instance.getConfig().getBoolean("debug-mode")) { instance.getLogger().info("Found home"); }
                    
                    homes.set("homes." + uuid + "." + args[0], null);

                    try {
                        File file = new File(instance.getDataFolder(), "homes.yml");
                        homes.save(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    sender.sendMessage("We have deleted your home " + i);
                    return true;
                } else {
                    continue;
                }
            }

            String _a = "We could not find a home named '%s'";
            sender.sendMessage(String.format(_a, args[0]));

            return true;
        } else {
            return true;
        }

    }
}
