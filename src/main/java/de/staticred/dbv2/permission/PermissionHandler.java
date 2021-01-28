package de.staticred.dbv2.permission;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.permission.db.PermissionDBDAO;
import de.staticred.dbv2.player.MemberSender;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

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


    /**
     * Constructor
     * @param useSQL whether to use sql or the file system
     */
    public PermissionHandler(boolean useSQL) {
        this.useSQL = useSQL;
        this.dao = new PermissionDBDAO(DBUtil.getINSTANCE().getDataBaseConnector());
        dao.startDAO();
    }


    /**
     * Checks if a member has permission
     * @return true if has
     */
    public boolean hasPermission(Member member, String permission) {
        return member.getRoles().stream().anyMatch(role -> {
            try {
                return dao.hasPermission(role.getIdLong(), permission);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
        });
    }

    public void setPermission(long role, String permission) {
        try {
            dao.setPermission(role, permission, true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Role> getInheritRoles(long role) {
        try {
            return dao.getInheritingRoles(role);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public Map<String, Boolean> getPermission(long id, boolean deep) {
        try {
            return dao.getPermissions(id, deep);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    public void removePermission(long id, String permission) {
        try {
            dao.removePermission(id, permission);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addInherit(long id, long inherit) {
        try {
            dao.addInherit(id, inherit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeInherit(long id, long inherit) {
        try {
            dao.removeInherit(id, inherit);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
