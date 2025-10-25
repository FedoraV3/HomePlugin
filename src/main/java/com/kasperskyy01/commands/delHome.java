package com.kasperskyy01.commands;

import com.kasperskyy01.database.databaseHandler;
import com.kasperskyy01.homePlugin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"PatternVariableCanBeUsed", "deprecation"})
public class delHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (sender instanceof Player && args.length > 0) {
            final homePlugin instance = homePlugin.getPlugin(homePlugin.class);
            Player p100 = (Player) sender;
            String homeName = args[0];
            String uniqueID = p100.getUniqueId().toString();

            databaseHandler dbHandler = instance.getClassDB();

            p100.sendMessage(ChatColor.GOLD + "Deleting home " + ChatColor.RED + homeName);
            if (dbHandler.getPlayerHome(uniqueID, homeName).isPresent()) {
                if (dbHandler.deletePlayerHome(uniqueID, homeName)) {
                    p100.sendMessage(ChatColor.GOLD + "Successfully deleted home " + ChatColor.RED + homeName);
                } else {
                    p100.sendMessage(ChatColor.GOLD + "Failed to delete home " + ChatColor.RED + homeName);
                }
            } else {
                p100.sendMessage(ChatColor.RED + "Home " + homeName + " doesn't exist!");
            }


        }
        return true;
    }
}
