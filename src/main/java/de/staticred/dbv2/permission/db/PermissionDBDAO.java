package de.staticred.dbv2.permission.db;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.networking.DAO;
import de.staticred.dbv2.networking.db.DataBaseConnector;
import de.staticred.dbv2.permission.PermissionDAO;
import de.staticred.dbv2.util.BotHelper;
import net.dv8tion.jda.api.entities.Role;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO to access all the permission data from the database
 *
 * @author Devin
 * @version 1.0.0
 */
public class PermissionDBDAO implements DAO, PermissionDAO {

    public final static String DISCORD_PERMISSION = "dcpermissions";
    public final static String DISCORD_PERMISSION_INHERIT = "dcinherits";

    public final static String DISCORD_ROLE = "discord_role";
    public final static String DISCORD_PERMISSION_VAL = "discord_permission";
    public final static String PERMISSION_ENABLED = "permission_enabled";
    public final static String DISCORD_ROLE_INHERIT = "discord_role_inherit";

    /**
     * Connection to the databse
     */
    private final DataBaseConnector connector;

    /**
     * Constructor.
     * @param connector connection to the db
     */
    public PermissionDBDAO(DataBaseConnector connector) {
        this.connector = connector;
    }

    @Override
    public boolean startDAO() {
        try {
            connector.executeUpdate("CREATE TABLE IF NOT EXISTS " + DISCORD_PERMISSION + " ("+ DISCORD_ROLE + " LONG, " + DISCORD_PERMISSION_VAL + " VARCHAR(255), "+ PERMISSION_ENABLED + " BOOLEAN)");
            connector.executeUpdate("CREATE TABLE IF NOT EXISTS " + DISCORD_PERMISSION_INHERIT + " (" + DISCORD_ROLE + " LONG, " + DISCORD_ROLE_INHERIT + " LONG)");
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
     */
    public List<Role> getRoles() throws SQLException {
        Connection con = connector.getNewConnection();

        ArrayList<Role> roles = new ArrayList<>();

        connector.logMessage("");

        PreparedStatement ps = con.prepareStatement("SELECT * FROM " + DISCORD_PERMISSION);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            long id = rs.getLong(DISCORD_ROLE);

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

        connector.executeUpdate("INSERT INTO "+ DISCORD_PERMISSION + " ("+ DISCORD_ROLE + ", " + DISCORD_PERMISSION_VAL + ", "+ PERMISSION_ENABLED +") VALUES(?, ?, ?)", role, permission, true);
    }

    public void removePermission(long role, String permission) throws SQLException {
        connector.executeUpdate("REMOVE from "+ DISCORD_PERMISSION + " WHERE " + DISCORD_ROLE + " = ?" + " AND WHERE " + DISCORD_PERMISSION_VAL + " = ?", role, permission);
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

        connector.executeUpdate("INSERT INTO " + DISCORD_PERMISSION_INHERIT + " (" + DISCORD_ROLE + ", " + DISCORD_ROLE_INHERIT + ") VALUES(?, ?)", role, inherit);
    }

    private boolean hasInheritEntry(long role, long inherit) throws SQLException {
        Connection con = connector.getNewConnection();

        connector.logMessage("Checking if role " + role + " has inherit entry.");

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS total FROM " + DISCORD_PERMISSION_INHERIT  + " WHERE " + DISCORD_ROLE + " = ? AND " + DISCORD_ROLE_INHERIT + " = ?");
        ps.setLong(1, role);
        ps.setLong(2, inherit);

        ResultSet rs = ps.executeQuery();

        int total = 0;

        if (rs.next())
            total = rs.getInt("total");

        con.close();
        ps.close();
        rs.close();

        connector.logMessage("Has inherit entry: " + (total > 0));

        return total > 0;
    }


    private boolean hasPermissionEntry(long role, String permission) throws SQLException {
        Connection con = connector.getNewConnection();

        connector.logMessage("Checking if role " + role + " has permission entry.");

        PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) AS total FROM " + DISCORD_PERMISSION + " WHERE " + DISCORD_ROLE + " = ? AND " + DISCORD_PERMISSION_VAL + " = ?");
        ps.setLong(1, role);
        ps.setString(2, permission);

        ResultSet rs = ps.executeQuery();

        int total = 0;
        if (rs.next())
             total = rs.getInt("total");

        con.close();
        ps.close();
        rs.close();

        connector.logMessage("Has permission entry: " + (total > 0));

        return total > 0;
    }

    public void addRole(long id) {
    }


    /**
     * Returns from which roles this role inherits
     * @param role to check on
     * @return list containing all roles its inheriting from
     */
    public List<Role> getInheritingRoles(long role) throws SQLException {
        Connection con = connector.getNewConnection();

        PreparedStatement ps = con.prepareStatement("SELECT " + DISCORD_ROLE_INHERIT + " FROM " + DISCORD_PERMISSION_INHERIT  + " WHERE " + DISCORD_ROLE + " = ?");
        ps.setLong(1, role);

        ResultSet rs = ps.executeQuery();

        ArrayList<Role> list = new ArrayList<>();

        while (rs.next()) {

            long inheritRole = rs.getLong("" + DISCORD_ROLE_INHERIT + "");

            Role inheritDCRole = BotHelper.jda.getRoleById(inheritRole);

            if (inheritDCRole == null) {
                removeInherit(role, inheritRole);
            } else {
                list.add(inheritDCRole);
            }
        }

        ps.close();
        rs.close();
        con.close();

        return list;

    }

    /**
     * removes the inherit role from a certain role
     * @param role the inherit will be removed from
     * @param inherit role
     */
    public void removeInherit(long role, long inherit) throws SQLException {
        connector.executeUpdate("DELETE FROM " + DISCORD_PERMISSION_INHERIT  + " WHERE " + DISCORD_ROLE + " = ? AND " + DISCORD_ROLE_INHERIT + " = ?", role, inherit);
    }


    /**
     * Returns all assigned permission connected to that group
     * @param role to get the permission from
     * @param deep when true it will also return the permission of the inherit groups
     * @return list containing the permission
     */
    public Map<String, Boolean> getPermissions(long role, boolean deep) throws SQLException {
        HashMap<String, Boolean> map = new HashMap<>();

        if (deep) {
            for (Role inheritRole : getInheritingRoles(role)) {
                map.putAll(getPermissions(inheritRole.getIdLong()));
            }
        }

        map.putAll(getPermissions(role));

        return map;
    }


    private Map<String, Boolean> getPermissions(long role) throws SQLException {
        Connection con = connector.getNewConnection();

        PreparedStatement ps = con.prepareStatement("SELECT * FROM " + DISCORD_PERMISSION + " WHERE " + DISCORD_ROLE + " = ?");
        ps.setLong(1, role);

        HashMap<String, Boolean> map = new HashMap<>();

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            map.put(rs.getString(DISCORD_PERMISSION_VAL), rs.getBoolean(PERMISSION_ENABLED));
        }

        rs.close();
        ps.close();
        con.close();
        return map;
    }


    /**
     * checks if the given role has permission or its roles its inheriting from
     * @param role role
     * @param permission to check on
     * @return true if he has the permissison
     */
    public boolean hasPermission(long role, String permission) throws SQLException {
        return getPermissions(role, true).containsKey(permission) && getPermissions(role, true).get(permission);
    }

    /**
     * Removes a role and all its content from the database
     * @param id role to delete
     * @throws SQLException
     */
    public void removeRole(long id) throws SQLException {
        connector.executeUpdate("DELETE FROM " + DISCORD_PERMISSION + " WHERE " + DISCORD_ROLE + " = ?", id);
    }

    @Override
    public void setEnabledState(long id, String permission, boolean state) throws SQLException {

        if (!hasPermissionEntry(id, permission)) {
            setPermission(id, permission, state);
            return;
        }

        connector.executeUpdate("UPDATE " + DISCORD_PERMISSION + " SET " + PERMISSION_ENABLED + " = ? WHERE " + DISCORD_ROLE + " = ? AND " + DISCORD_PERMISSION_VAL + " = ?", state, id, permission);
    }

    @Override
    public boolean saveData() throws IOException {
        return false;
    }

    @Override
    public YamlFile asYaml() {
        YamlFile conf = new YamlFile();

        try {
            for (Role role : getRoles()) {
                List<String> permissions = new ArrayList<String>();
                List<String> enabled = new ArrayList<String>();
                List<String> inheritList = new ArrayList<String>();

                Map<String, Boolean> permissionMap = getPermissions(role.getIdLong());

                for (String permission : permissionMap.keySet()) {
                    permissions.add(permission);
                    if (permissionMap.get(permission)) {
                        enabled.add(permission);
                    }
                }

                for (Role inherit : getInheritingRoles(role.getIdLong())) {
                    inheritList.add(inherit.getId());
                }
                conf.set(role.getId() + ".permission", permissions);
                conf.set(role.getId() + ".inherit", inheritList);
                conf.set(role.getId() + ".enabled", enabled);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return conf;
    }
}
