package com.kasperskyy01.commands;

import com.kasperskyy01.homePlugin;
import com.kasperskyy01.database.databaseHandler;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class setHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player && args.length > 0) {

            final homePlugin clazz = homePlugin.getPlugin(homePlugin.class);
            @SuppressWarnings("PatternVariableCanBeUsed") Player p100 = (Player) sender;
            String uniqueID = p100.getUniqueId().toString();

            String homeName = args[0];
            Location location = p100.getLocation();

            databaseHandler dbHandler = clazz.getClassDB();


            // Check if home exists so we don't delete it
            if (dbHandler.getPlayerHome(uniqueID, homeName).isPresent()) {
                sender.sendMessage(ChatColor.GOLD + "You already have a home named " + homeName);
                return true;
            }

            p100.sendMessage(ChatColor.GOLD + "Setting home " + ChatColor.RED + homeName + "..");

            // Set the home successfully
            if (dbHandler.setPlayerHome(p100.getUniqueId().toString(), homeName, location)) {
                sender.sendMessage(ChatColor.GOLD + "Set home " + ChatColor.RED + homeName + ChatColor.GOLD + " successfully!");
            } else {
                sender.sendMessage(ChatColor.GOLD + "Something went wrong!");
            }

        }

        return true;
    }
}
