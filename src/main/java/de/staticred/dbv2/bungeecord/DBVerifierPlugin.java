package de.staticred.dbv2.bungeecord;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.bungeecord.events.CommandEvent;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Class extending Plugin used if a bungeecord plugin
 *
 * starts DBVerifier
 *
 * @author Devin
 * @version 1.0.0
 */
public class DBVerifierPlugin extends Plugin {

    @Override
    public void onEnable() {
        new DBUtil(Mode.BUNGEECORD, new ConsoleLogger());

        getProxy().getPluginManager().registerListener(this, new CommandEvent());
    }
}
