package de.staticred.dbv2.proxies.bungeecord;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.BukkitPlayer;
import de.staticred.dbv2.player.BungeePlayer;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.proxies.bungeecord.commands.DBCommand;
import de.staticred.dbv2.proxies.bungeecord.events.BungeeEventManager;
import de.staticred.dbv2.proxies.bungeecord.events.CommandEvent;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import de.staticred.dbv2.util.Proxy;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;

/**
 * Class extending Plugin used if a bungeecord plugin
 *
 * starts DBVerifier
 *
 * @author Devin
 * @version 1.0.0
 */
public class DBVerifierPlugin extends Plugin implements Proxy {

    private static DBVerifierPlugin INSTANCE;

    private DBUtil dbUtil;

    @Override
    public void onEnable() {
        try {
           dbUtil = new DBUtil(this, new BungeeEventManager(), Mode.BUNGEECORD, new ConsoleLogger(DBUtil.PLUGIN_NAME));
        } catch (IOException exception) {
            exception.printStackTrace();
            return;
        }

        INSTANCE = this;

        getProxy().getPluginManager().registerCommand(this, new DBCommand("dbu"));
        getProxy().getPluginManager().registerListener(this, new CommandEvent());
        //Metrics metrics = new Metrics(this, DBUtil.PLUGIN_ID);
    }

    @Override
    public DBUPlayer getOnlinePlayer(String name) {
        ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);

        return player != null ? new BungeePlayer(player) : null;
    }

    @Override
    public void onDisable() {
        dbUtil.shutDown();
    }

    public static DBVerifierPlugin getInstance() {
        return INSTANCE;
    }
}
