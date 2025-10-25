package com.kasperskyy01.database.TabCompletion;

import com.kasperskyy01.homePlugin;
import com.kasperskyy01.database.databaseHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        if (sender instanceof Player) {
            databaseHandler dbHandler = homePlugin.getPlugin(homePlugin.class).getClassDB();
            //noinspection PatternVariableCanBeUsed
            Player p100 = (Player) sender;
            String uniqueID = p100.getUniqueId().toString();

            Optional<List<String>> homes = dbHandler.getHomes(uniqueID);
            if (homes.isPresent()) {
                return homes.get();
            }
        }

        return null;
    }
}
