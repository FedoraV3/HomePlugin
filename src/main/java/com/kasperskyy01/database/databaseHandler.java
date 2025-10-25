package com.kasperskyy01.database;

import com.kasperskyy01.data.Records;
import com.kasperskyy01.homePlugin;

import java.sql.*;
import java.util.Arrays;
import java.util.Optional;

import org.bukkit.Location;

@SuppressWarnings("UnnecessarySemicolon")
public class databaseHandler {
    public String dbPath;

    public Optional<Records> getPlayerHome(String uniqueID, String home) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        ) {

            // Query for the home
            String query = "SELECT x, y, z, world FROM home WHERE player = ? AND home_name = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, uniqueID);
            ps.setString(2, home);

            ResultSet resolution = ps.executeQuery();

            // Checks
            if (!resolution.next()) { return Optional.empty(); }

            // Init record variable and read query results
            String world = resolution.getString("world");
            double x = resolution.getDouble("x");
            double y = resolution.getDouble("y");
            double z = resolution.getDouble("z");

            Records records = new Records(world, x, y, z);

            return Optional.of(records);
        } catch (SQLException e) {
            final homePlugin clazz = homePlugin.getPlugin(homePlugin.class);
            clazz.getLogger().severe(Arrays.toString(e.getStackTrace()));
            return Optional.empty();
        }
    }

    public boolean setPlayerHome(String uniqueID, String homeName, Location l100) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        ) {

            final homePlugin clazz = homePlugin.getPlugin(homePlugin.class);
            double x  = l100.getX();
            double y = l100.getY();
            double z = l100.getZ();
            String world = l100.getWorld().getName();

            String query = "INSERT OR REPLACE INTO home (player, home_name, x, y, z, world) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, uniqueID);
            ps.setString(2, homeName);
            ps.setDouble(3, x);
            ps.setDouble(4, y);
            ps.setDouble(5, z);
            ps.setString(6, world);

            ps.executeUpdate();

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

    public boolean deletePlayerHome(String uniqueID, String home) {
        try (
                Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
        ) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM home WHERE player = ? AND home_name = ?;");

            ps.setString(1, uniqueID);
            ps.setString(2, home);

            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            final homePlugin clazz = homePlugin.getPlugin(homePlugin.class);
            clazz.getLogger().severe(Arrays.toString(e.getStackTrace()));
            return false;
        }
    }


    @SuppressWarnings("UnnecessarySemicolon")
    public void initializeDB() {
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