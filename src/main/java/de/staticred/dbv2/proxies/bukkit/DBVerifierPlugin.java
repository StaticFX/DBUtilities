package de.staticred.dbv2.proxies.bukkit;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.proxies.bukkit.events.BukkitEventManager;
import de.staticred.dbv2.proxies.bukkit.events.CommandEvent;
import de.staticred.dbv2.player.BukkitPlayer;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import de.staticred.dbv2.util.Proxy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class DBVerifierPlugin extends JavaPlugin implements Proxy {

    private static DBVerifierPlugin INSTANCE;

    private DBUtil dbUtil;

    @Override
    public void onEnable() {

        try {
            dbUtil = new DBUtil(this, new BukkitEventManager(), Mode.BUKKIT, new ConsoleLogger(DBUtil.PLUGIN_NAME));
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }


        INSTANCE = this;

        getServer().getPluginManager().registerEvents(new CommandEvent(), this);

    }

    @Override
    public void onDisable() {
        dbUtil.shutDown();
    }


    @Override
    public DBUPlayer getOnlinePlayer(String name) {
        Player player = Bukkit.getPlayer(name);

        return player != null ? new BukkitPlayer(player) : null;
    }

    public static DBVerifierPlugin getInstance() {
        return INSTANCE;
    }
}
