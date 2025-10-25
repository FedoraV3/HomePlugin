package com.kasperskyy01.commands;

import com.kasperskyy01.homePlugin;
import com.kasperskyy01.database.databaseHandler;
import com.kasperskyy01.data.Records;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class Home implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player & args.length > 0) {
            final homePlugin instance = homePlugin.getPlugin(homePlugin.class);
            Player p100 = (Player) sender;
            String UniqueID = p100.getUniqueId().toString();
            String homeName = args[0];

            databaseHandler dbHandler = instance.getClassDB();

            Optional<Records> r = dbHandler.getPlayerHome(UniqueID, homeName);

            if (r.isPresent()) {
                sender.sendMessage(ChatColor.GOLD + "Teleporting to " +  ChatColor.RED + homeName + "...");

                String world = r.get().world();
                double x = r.get().x();
                double y = r.get().y();
                double z = r.get().z();

                Location l100 = new Location(Bukkit.getWorld(world), x, y, z);
                p100.teleport(l100);

                sender.sendMessage(ChatColor.GOLD + "Teleported to " +  ChatColor.RED + homeName + "!");
            } else {
                sender.sendMessage(ChatColor.RED + "That home doesn't exist!");
            }
        }

        return true;
    }
}
