package de.staticred.dbv2.bukkit;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.bukkit.events.BukkitEventManager;
import de.staticred.dbv2.bukkit.events.CommandEvent;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.sql.SQLException;

public class DBVerifierPlugin extends JavaPlugin {

    private static DBVerifierPlugin INSTANCE;

    private DBUtil dbUtil;

    @Override
    public void onEnable() {

        try {
            dbUtil = new DBUtil(new BukkitEventManager(), Mode.BUKKIT, new ConsoleLogger());
        } catch (IOException exception) {
            exception.printStackTrace();
        }


        INSTANCE = this;

        getServer().getPluginManager().registerEvents(new CommandEvent(), this);

    }

    @Override
    public void onDisable() {
        dbUtil.shutDown();
    }

    public static DBVerifierPlugin getInstance() {
        return INSTANCE;
    }
}
