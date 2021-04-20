package de.staticred.dbv2.velocity;

import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.events.util.EventManager;
import de.staticred.dbv2.util.ConsoleLogger;
import de.staticred.dbv2.util.Mode;
import de.staticred.dbv2.velocity.events.CommandEvent;
import de.staticred.dbv2.velocity.events.VelocityEventManager;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */

@Plugin(id = "db-utilities", name = "DBUtilities", version = "2.0.0", authors = {"StaticRed"})
public class DBVerifierPlugin {

    private static DBVerifierPlugin INSTANCE;

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public DBVerifierPlugin(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
        
        VelocityEventManager eventManager = new VelocityEventManager();
        ConsoleLogger logger1 = new ConsoleLogger();

        try {
            DBUtil dbUtil = new DBUtil(eventManager, Mode.VELOCITY, logger1);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        eventManager.registerEvent(new CommandEvent());

    }

    public ProxyServer getServer() {
        return server;
    }

    public static DBVerifierPlugin getInstance() {
        return INSTANCE;
    }
}
