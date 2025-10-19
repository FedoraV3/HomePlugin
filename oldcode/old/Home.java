package com.kasperskyy01.home.old;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import com.kasperskyy01.home.HomePlugin;


public class Home implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String _u, String[] args) {
        if (sender instanceof Player && args.length > 0) {
            final HomePlugin instance = HomePlugin.getPlugin(HomePlugin.class);
            FileConfiguration a = instance.getHomeYML();

            Player player = Bukkit.getPlayer(sender.getName());
            String uuid = player.getUniqueId().toString();
            ConfigurationSection playerHomes = a.getConfigurationSection("homes." + uuid);

            if (playerHomes == null) {
                sender.sendMessage("You don't have any homes!");
                return true;
            }

            var keys = playerHomes.getKeys(false);

            for (String _t : keys) {
                if (_t.equalsIgnoreCase(args[0])) {
                    player.sendMessage("Found your home! Teleporting now..");
                    ConfigurationSection coordinates = a.getConfigurationSection("homes." + uuid + "." + _t);
                    var values = coordinates.getValues(false);

                    World world = (World) Bukkit.getWorld((String) values.get("world"));
                    double x = (double) values.get("x");
                    double y = (double) values.get("y");
                    double z = (double) values.get("z");

                    Location plrHome = new Location(world, x, y, z);
                    player.teleport(plrHome);
                    return true;
                } else {
                    continue;
                }
            }

            String _a = "We could not find a home named '%s'";
            sender.sendMessage(String.format(_a, args[0]));
            return true;
        } else {
            return false;
        }
    }
}
