package de.staticred.dbv2.permission;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.permission.db.PermissionDBDAO;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.sql.SQLException;

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




}
