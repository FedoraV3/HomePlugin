package com.kasperskyy01.home;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;


public class NHome implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command giggabyte, String giggabyte2, String[] args) {
        if (sender instanceof Player && sender.hasPermission("home.use")) {
            final HomePlugin instance = HomePlugin.getClass(HomePlugin.class);
            Player player = Bukkit.getPlayer(sender.getName());
            String path = "homes." + player.getUniqueId().toString() + args[0];
            List<String> Coords = instance.YMLMgr.getLocation(path);


        }
        return true;
    }
}
