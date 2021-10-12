package de.staticred.dbv2.proxies.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.mcimplementations.VelocityPlayer;
import de.staticred.dbv2.proxies.velocity.events.VelocityChatEvent;
import de.staticred.dbv2.proxies.velocity.events.VelocityEventManager;
import de.staticred.dbv2.proxies.velocity.events.VelocityJoinEvent;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import de.staticred.dbv2.util.Proxy;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */

@Plugin(id = "db-utilities", name = "DBUtilities", version = "2.0.0", authors = {"StaticRed"})
public class DBVerifierPlugin implements Proxy {

    private static DBVerifierPlugin INSTANCE;

    private final ProxyServer server;
    private final Logger logger;
    private DBUtil dbUtil;

    private VelocityEventManager eventManager;

    @Inject
    public DBVerifierPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        ConsoleLogger logger1 = new ConsoleLogger(DBUtil.PLUGIN_NAME);

        try {
            dbUtil = new DBUtil(this, eventManager, Mode.VELOCITY, logger1);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public DBUPlayer getOnlinePlayer(String name) {
        Optional<Player> player = server.getPlayer(name);

        return player.map(VelocityPlayer::new).orElse(null);
    }

    @Override
    public void executeConsoleCommand(String command) {
        DBUtil.getINSTANCE().getLogger().postMessage("Executing console command: " + command);
        server.getCommandManager().executeImmediatelyAsync( server.getConsoleCommandSource(), command);
    }

    @Override
    public DBUPlayer getPlayer(UUID uuid) {
        Optional<Player> player = server.getPlayer(uuid);

        return player.map(VelocityPlayer::new).orElse(null);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        eventManager = new VelocityEventManager();
        eventManager.init();
        eventManager.registerEvent(new VelocityJoinEvent());
        eventManager.registerEvent(new VelocityChatEvent());
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event) {
        if (dbUtil != null)
            dbUtil.shutDown();
    }

    public ProxyServer getServer() {
        return server;
    }

    public static DBVerifierPlugin getInstance() {
        return INSTANCE;
    }
}
