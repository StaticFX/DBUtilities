package de.staticred.dbv2.bungeecord;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.bungeecord.events.BungeeEventManager;
import de.staticred.dbv2.bungeecord.events.CommandEvent;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Class extending Plugin used if a bungeecord plugin
 *
 * starts DBVerifier
 *
 * @author Devin
 * @version 1.0.0
 */
public class DBVerifierPlugin extends Plugin {

    private static DBVerifierPlugin INSTANCE;

    @Override
    public void onEnable() {
        try {
            new DBUtil(new BungeeEventManager(), Mode.BUNGEECORD, new ConsoleLogger());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        INSTANCE = this;

        getProxy().getPluginManager().registerListener(this, new CommandEvent());
        //Metrics metrics = new Metrics(this, DBUtil.PLUGIN_ID);
    }

    public static DBVerifierPlugin getInstance() {
        return INSTANCE;
    }
}
