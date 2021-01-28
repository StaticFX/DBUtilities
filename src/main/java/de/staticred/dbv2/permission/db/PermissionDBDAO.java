package de.staticred.dbv2.permission.db;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.networking.DAO;
import de.staticred.dbv2.networking.db.DataBaseConnector;
import de.staticred.dbv2.util.BotHelper;
import net.dv8tion.jda.api.entities.Role;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO to access all the permisison data from the database
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public class PermissionDBDAO implements DAO {

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
    public PermissionDBDAO(DataBaseConnector connector, boolean useSQL) {
        this.connector = connector;
        this.useSQL = useSQL;
    }

    @Override
    public boolean startDAO() {
        try {
            connector.executeUpdate("CREATE TABLE IF NOT EXISTS discord.permission (discord_role LONG, discord_permission VARCHAR(255), permission_enabled BOOLEAN)");
            connector.executeUpdate("CREATE TABLE IF NOT EXISTS discord.permission.inherit (discord_role LONG, discord_role_inherit LONG)");

        } catch (SQLException throwables) {
            DBUtil.getINSTANCE().getLogger().postError("Can't init database for unknown reason.");
            throwables.printStackTrace();
            return false;
        }
        return false;
    }


    /**
     * returns all roles and removes roles if they're not on the guild anymore
     * @return all roles
     * @throws SQLException
     */
    public List<Role> getRoles() throws SQLException {
        Connection con = connector.getNewConnection();

        ArrayList<Role> roles = new ArrayList<>();

        connector.logMessage("");

        PreparedStatement ps = con.prepareStatement("SELECT discord_role FROM discord.permission");

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            long id = rs.getLong("discord_role");

            Role role = BotHelper.jda.getRoleById(id);

            if (role == null) {
                removeRole(id);
            } else {
                roles.add(role);
            }
        }

        ps.close();
        rs.close();
        con.close();

        return roles;
    }

    /**
     * adds the permission to the group
     * @param role role which will gain the permission
     * @param permission to add
     * @param enabled enabled
     */
    public void setPermission(long role, String permission, boolean enabled) throws SQLException {
        if (hasPermissionEntry(role, permission))
            return;

        connector.executeUpdate("INSERT INTO discord.permission (discord_role, discord_permission, permission_enabled) VALUES(?, ?, ?)", role, permission, true);
    }

    /**
     * adds an group it will inherit from
     *
     * means it will grant all permission the group it inherits from also has
     *
     * @param role to from
     * @param inherit
     */
    public void addInherit(long role, long inherit) throws SQLException {
        if (hasInheritEntry(role, inherit))
            return;

        connector.executeUpdate("INSERT INTO discord.permission.inherit (discord_role, discord.permission.inherit) VALUES(?, ?)", role, inherit);
    }

    private boolean hasInheritEntry(long role, long inherit) throws SQLException {
        Connection con = connector.getNewConnection();

        connector.logMessage("Checking if role " + role + " has inherit entry.");

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS total FROM discord.permission.inherit WHERE discord_role = ? AND WHERE discord_role_inherit = ?");
        ps.setLong(1, role);
        ps.setLong(1, inherit);

        ResultSet rs = ps.executeQuery();

        int total = rs.getInt("total");

        con.close();
        ps.close();
        rs.close();

        connector.logMessage("Has inherit entry: " + (total > 0));

        return total > 0;
    }



    private boolean hasPermissionEntry(long role, String permission) throws SQLException {
        Connection con = connector.getNewConnection();

        connector.logMessage("Checking if role " + role + " has permission entry.");

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS total FROM discord.permission WHERE discord_role = ? AND WHERE discord_permission = ?");
        ps.setLong(1, role);
        ps.setString(1, permission);

        ResultSet rs = ps.executeQuery();

        int total = rs.getInt("total");

        con.close();
        ps.close();
        rs.close();

        connector.logMessage("Has permission entry: " + (total > 0));

        return total > 0;
    }


    /**
     * checks if a role has permission
     * @param role role
     * @param permission to check on
     * @return true if he has the permissison
     */
    public boolean hasPermission(long role, String permission) throws SQLException {
        Connection con = connector.getNewConnection();

        connector.logMessage("Checking if role " + role + " has permission.");

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS total FROM discord.permission WHERE discord_role = ? AND WHERE discord_permission = ? AND WHERE permission_enabled = TRUE");
        ps.setLong(1, role);
        ps.setString(1, permission);

        ResultSet rs = ps.executeQuery();

        int total = rs.getInt("total");

        con.close();
        ps.close();
        rs.close();

        connector.logMessage("Has permission: " + (total > 0));

        return total > 0;
    }






    /**
     * Removes a role and all its content from the database
     * @param id role to delete
     * @throws SQLException
     */
    public void removeRole(long id) throws SQLException {
        connector.executeUpdate("DELETE FROM discord.permission WHERE discord_role = ?", id);
    }






    @Override
    public boolean saveData() throws IOException {
        return false;
    }
}
