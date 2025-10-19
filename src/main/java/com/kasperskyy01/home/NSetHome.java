package com.kasperskyy01.home;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NSetHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command _unused1, String _unused2, String[] args) {
        HashMap<String, Object> toSet = new HashMap<String, Object>();
        if (sender instanceof Player) {
            final HomePlugin instance = HomePlugin.getPlugin(HomePlugin.class);
            Player player = Bukkit.getPlayer(sender.getName());
            String uuid = player.getUniqueId().toString();
            String path = "homes." + uuid + "." + args[0];

            toSet.put(path + ".world", player.getWorld().getName());
            toSet.put(path + ".x", player.getX());
            toSet.put(path + ".y", player.getY());
            toSet.put(path + ".z", player.getZ());
            
            instance.YMLMgr.WriteToYAML(player, toSet);

            return true;
        }

        return true;
    }
}