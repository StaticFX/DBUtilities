package de.staticred.dbv2.bukkit;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.bukkit.events.CommandEvent;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class DBVerifierPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        try {
            new DBUtil(Mode.BUKKIT, new ConsoleLogger());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new CommandEvent(), this);
    }
}
