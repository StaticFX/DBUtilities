package de.staticred.dbv2.permission.db;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.networking.DAO;
import de.staticred.dbv2.networking.db.DataBaseConnector;

import java.io.IOException;
import java.sql.SQLException;

/**
 * DAO to access all the permisison data from the database
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public class PermissionDAO implements DAO {

    /**
     * Connection to the databse
     */
    private final DataBaseConnector connector;

    /**
     * Whether the program should use sql or not
     */
    private final boolean useSQL;

    /**
     * Constructor.
     * @param connector connection to the db
     * @param useSQL Whether the program should use sql or not
     */
    public PermissionDAO(DataBaseConnector connector, boolean useSQL) {
        this.connector = connector;
        this.useSQL = useSQL;
    }

    @Override
    public boolean startDAO() {
        try {
            connector.executeUpdate("CREATE TABLE IF NOT EXISTS discord.permission (discord_role LONG, permission VARCHAR(255), enabled BOOLEAN, discord_role_inherit LONG)");
        } catch (SQLException throwables) {
            DBUtil.getINSTANCE().getLogger().postError("Can't init database for unknown reason.");
            throwables.printStackTrace();
            return false;
        }
        return false;
    }


    






    @Override
    public boolean saveData() throws IOException {
        return false;
    }
}
