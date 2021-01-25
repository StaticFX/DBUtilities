package de.staticred.dbv2.networking.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import de.staticred.dbv2.files.ConfigObject;
import de.staticred.dbv2.files.FileConstants;
import de.staticred.dbv2.util.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Object which will directly connect with the database
 *
 * IMPORTANT:
 *
 * This plugin still connects via. jdbc to a database
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public class DataBaseConnector {

    /**
     * SQL user
     */
    private final String user;

    /**
     * SQL password
     */
    private final String password;

    /**
     * SQL port
     */
    private final int port;

    /**
     * SQL ssl
     */
    private final boolean ssl;

    /**
     * SQL database
     */
    private final String database;

    /**
     * SQL host
     */
    private final String host;

    /**
     * Logger which the all of database history will be logged to
     * if null it wont be logged
     */
    private Logger logger;


    /**
     * HikariConfig used to config the system
     */
    private final HikariConfig config;

    /**
     * HikariSource used to connect to the database
     */
    private HikariDataSource source;

    /**
     * Will create an DataBaseConnector object
     * @param config depend
     */
    public DataBaseConnector(ConfigObject config) {
        this.user = config.getString(FileConstants.SQL_USER);
        this.password = config.getString(FileConstants.SQL_PASSWORD);
        this.port = config.getInt(FileConstants.SQL_PORT);
        this.ssl = config.getBoolean(FileConstants.SQL_USE_SSL);
        this.host = config.getString(FileConstants.SQL_HOST);
        this.database = config.getString(FileConstants.SQL_DATABASE);

        this.config = new HikariConfig();
        this.config.setUsername(user);
        this.config.setPassword(password);
        this.config.setMaximumPoolSize(20);


        if (ssl) {
            this.config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        } else {
            this.config.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false");
        }
    }



    public void setLogger(Logger logger) {
        this.logger = logger;
    }




    /**
     * Will init/start the datasource
     */
    public void init() {
        this.source = new HikariDataSource(this.config);
    }

    public void executeUpdate(String string, Object... obj) throws SQLException {
        Connection connection = source.getConnection();

        logMessage("Opening Connection");

        PreparedStatement ps = connection.prepareStatement(string);
        for (int i = 0; i < obj.length; i++) {
            ps.setObject(i + 1, obj[i]);
        }
        ps.executeUpdate();

        logMessage("Executing SQL Update: " + string);

        ps.close();
        connection.close();
        logMessage("Closing Connection");
    }

    /**
     * closes the connection
     */
    public void shutDown() {
        source.close();
    }



    private void logMessage(String message) {
        if (logger != null)
            logger.postMessage(message);
    }


}
