package de.staticred.dbv2.permission;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.files.util.FileBackUpHelper;
import de.staticred.dbv2.permission.db.PermissionDBDAO;
import de.staticred.dbv2.permission.filemanager.PermissionFileDAO;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * The permission handler class will provide functions to add custom permission to discord roles
 *
 * @author Devin
 * @version 1.0.0
 */
public class PermissionHandler {

    /**
     * DAO to access the data
     */
    private final PermissionDAO dao;

    /**
     * whether to use sql or not
     */
    private final boolean useSQL;


    private final File permissionFile;


    /**
     * Constructor
     * @param useSQL whether to use sql or the file system
     */
    public PermissionHandler(boolean useSQL) throws IOException {
        this.useSQL = useSQL;
        this.permissionFile = new File(DBUtil.getINSTANCE().getDataFolder().getAbsolutePath(), "permissions.yml");

        if (useSQL)
            this.dao = new PermissionDBDAO(DBUtil.getINSTANCE().getDataBaseConnector());
        else
            this.dao = new PermissionFileDAO(permissionFile);
        dao.startDAO();
    }

    /**
     * Checks if a role has a permission
     * @param role the role
     * @param permission the permission
     * @return true if has
     */
    public boolean hasPermission(long role, String permission) {
        boolean b = false;
        try {
            if (dao.getPermissions(role, true).containsKey(permission))
                b = dao.getPermissions(role, true).get(permission);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return b;
    }

    /**
     * Checks if a member has permission
     * Owner of the guild will always have the permission
     *
     * @return true if has
     */
    public boolean hasPermission(Member member, String permission) {
        if (member.isOwner())
            return true;

        for (Role role : member.getRoles()) {
            Map<String, Boolean> map = getPermission(role.getIdLong(), true);

            if (map.containsKey("*")) {
                return map.get("*");
            }

            if (map.containsKey(permission)) {
                return map.get(permission);
            }



        }
        return false;
    }

    /**
     * sets a permission for a grouo
     * enabled will be true
     * @param role the role
     * @param permission the permission
     */
    public void setPermission(long role, String permission) {
        try {
            dao.setPermission(role, permission, true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * @param role the role
     * @return list of all roles the role is inheriting from
     */
    public List<Role> getInheritRoles(long role) {
        try {
            return dao.getInheritingRoles(role);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * @param id id
     * @param deep if it should also give all the inheriting permissions
     * @return permission map, key will be the permission, value if its enabled
     */
    public Map<String, Boolean> getPermission(long id, boolean deep) {
        try {
            return dao.getPermissions(id, deep);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    /**
     * removes permission from a role
     * @param id the role
     * @param permission the permission
     */
    public void removePermission(long id, String permission) {
        try {
            dao.removePermission(id, permission);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * adds inheriting role to another role
     * @param id to add the inherit to
     * @param inherit to add to the other role
     */
    public void addInherit(long id, long inherit) {
        try {
            dao.addInherit(id, inherit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * disables certain permission for a role
     * will lead into a false at
     * @see PermissionHandler#hasPermission(Member, String)
     * @param id the role
     * @param permission the permission
     */
    public void setEnabledState(boolean state, long id, String permission) {
        try {
            dao.setEnabledState(id, permission, state);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * removes inherit from a role
     * @param id the role
     * @param inherit the inherit
     */
    public void removeInherit(long id, long inherit) {
        try {
            dao.removeInherit(id, inherit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Removes a role from the filesystem
     * @param id the role
     */
    public void removeRole(long id) {
        try {
            dao.removeRole(id);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * adds a role to the filesystem
     * @param idLong the role
     */
    public void addRole(long idLong) {
        dao.addRole(idLong);
    }

    /**
     * Will store all the content into a file
     */
    public void writeToFile() {
        try {
            FileBackUpHelper.createBackUpFor("permissison-backup.yml", Long.toString(System.currentTimeMillis()), dao.asYaml());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
