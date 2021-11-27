package de.staticred.dbv2.permission;

import net.dv8tion.jda.api.entities.Role;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Interface to access the daos
 *
 * DAOs can access the file or the
 *
 */
public interface PermissionDAO {

    /**
     * Checks if the id has the permission
     *
     * @param id         to check on
     * @param permission to check with
     * @return true if he has the permission
     */
    boolean hasPermission(long id, String permission) throws SQLException;

    /**
     * start the internal services to access the db
     */
    boolean startDAO() throws IOException;

    /**
     * Adds permission to the given role
     *
     * @param id         role
     * @param permission to add
     * @throws SQLException
     */
    void setPermission(long id, String permission, boolean enabled) throws SQLException;

    /**
     * Returns from which roles this role inherits
     *
     * @param role to check on
     * @return list containing all roles its inheriting from
     */
    List<Role> getInheritingRoles(long role) throws SQLException;


    /**
     * Returns all assigned permission connected to that group
     *
     * @param role to get the permission from
     * @param deep when true it will also return the permission of the inherit groups
     * @return list containing the permission
     */
    public Map<String, Boolean> getPermissions(long role, boolean deep) throws SQLException;

    /**
     * Removes the permission
     * @param role from which the permission gets removes
     * @param permission to get removed
     * @throws SQLException
     */
    public void removePermission(long role, String permission) throws SQLException;


    /**
     * adds an group it will inherit from
     *
     * means it will grant all permission the group it inherits from also has
     *
     * @param role to from
     * @param inherit
     */
    public void addInherit(long role, long inherit) throws SQLException;


    /**
     * removes inherit from role
     * @param role inherit will be removed from
     * @param inherit inherit
     * @throws SQLException
     */
    public void removeInherit(long role, long inherit) throws SQLException;

    /**
     * Removes a role
     * @param role to remove
     * @throws SQLException
     */
    public void removeRole(long role) throws SQLException;

    /**
     * Adds empty role
     * @param idLong to add
     */
    void addRole(long idLong);

    /**
     * @return dao as yamlfile
     */
    YamlFile asYaml();

    /**
     * sets a permission to disabled
     * @param id of the role
     * @param permission the permission
     * @param state state of the permission
     */
    void setEnabledState(long id, String permission, boolean state) throws SQLException;
}
