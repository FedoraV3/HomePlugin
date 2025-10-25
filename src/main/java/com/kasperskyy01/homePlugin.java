// TODOS:
// TODO: Add logging in a file
// TODO: Polish it if yes

package com.kasperskyy01;

import com.kasperskyy01.commands.*;
import com.kasperskyy01.database.TabCompletion.TabCompletion;
import com.kasperskyy01.database.databaseHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class homePlugin extends JavaPlugin {

    private databaseHandler dbHandler;

    // Getters
    public databaseHandler getClassDB() {
        return dbHandler;
    }

    // Main
    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("[HomePlugin] Plugin has been enabled!");

        // Class
        dbHandler = new databaseHandler(getDataFolder() + "/homes.db");

        // Initialization
        saveDefaultConfig();
        dbHandler.initializeDB();

        // Commands
        this.getCommand("sethome").setExecutor(new setHome());
        this.getCommand("home").setExecutor(new Home());
        this.getCommand("delhome").setExecutor(new delHome());

        // Tab Completers
        this.getCommand("home").setTabCompleter(new TabCompletion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("[HomePlugin] Plugin has been disabled!");
    }

}