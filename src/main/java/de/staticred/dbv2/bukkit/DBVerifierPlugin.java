package de.staticred.dbv2.bukkit;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.bukkit.events.CommandEvent;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import org.bukkit.plugin.java.JavaPlugin;

public class DBVerifierPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        new DBUtil(Mode.BUKKIT, new ConsoleLogger());

        getServer().getPluginManager().registerEvents(new CommandEvent(), this);
    }
}
