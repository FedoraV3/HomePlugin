package com.kasperskyy01.database;

import com.kasperskyy01.data.HomeData;
import com.kasperskyy01.homePlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Optional;

import org.bukkit.Location;

// Sowwy I chatgptted the DB commands
@SuppressWarnings("UnnecessarySemicolon")
public class databaseHandler {
    public String dbPath;

    public Optional<HomeData> getPlayerHome(String uniqueID, String home) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
                Statement statement = connection.createStatement();
        ) {

            // Query for the home
            String query = String.format("SELECT x, y, z, world FROM home WHERE player = '%s' AND home_name = '%s';", uniqueID, home);
            ResultSet resolution = statement.executeQuery(query);

            // Checks
            if (!resolution.next()) { return Optional.empty(); }

            // Init record variable & read query results
            String world = resolution.getString("world");
            double x = resolution.getDouble("x");
            double y = resolution.getDouble("y");
            double z = resolution.getDouble("z");

            HomeData homeData = new HomeData(world, x, y, z);

            return Optional.of(homeData);
        } catch (SQLException e) {
            final homePlugin clazz = homePlugin.getPlugin(homePlugin.class);
            clazz.getLogger().severe(Arrays.toString(e.getStackTrace()));
            return Optional.empty();
        }
    }

    public boolean setPlayerHome(String uniqueID, String homeName, Location l100) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
                Statement statement = connection.createStatement();
        ) {

            final homePlugin clazz = homePlugin.getPlugin(homePlugin.class);
            double x  = l100.getX();
            double y = l100.getY();
            double z = l100.getZ();
            String world = l100.getWorld().getName();

            String query = String.format("INSERT OR REPLACE INTO home (player, home_name, x, y, z, world) VALUES ('%s', '%s', %f, %f, %f, '%s');", uniqueID, homeName, x, y, z, world);
            statement.executeUpdate(query);

            // Checking if it exists now that we created so if something happens, yeah
            if (getPlayerHome(uniqueID, homeName).isPresent()) {
                return true;
            } else {
                // TODO: This is where the logging is added
                clazz.getLogger().severe("Something went wrong when adding " + uniqueID + " to the list");
                return false;
            }


        } catch (SQLException e) {
            final homePlugin clazz = homePlugin.getPlugin(homePlugin.class);
            clazz.getLogger().severe(Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


    @SuppressWarnings("UnnecessarySemicolon")
    public void initalizeDB() {
        final homePlugin clazz = homePlugin.getPlugin(homePlugin.class);
        // It will always run
        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
                Statement statement = connection.createStatement();
        ) {

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS home (player TEXT NOT NULL, home_name TEXT NOT NULL, x REAL NOT NULL, y REAL NOT NULL, z REAL NOT NULL, world TEXT NOT NULL, PRIMARY KEY (player, home_name));");
            if (clazz.getConfig().getBoolean("debug-mode")) {
               clazz.getLogger().info("Init Home DB");
            }

        } catch (SQLException e) {
            clazz.getLogger().severe(Arrays.toString(e.getStackTrace()));
        }

    }
}