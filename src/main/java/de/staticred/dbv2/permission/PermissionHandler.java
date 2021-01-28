package de.staticred.dbv2.permission;

import de.staticred.dbv2.permission.db.PermissionDAO;
import net.dv8tion.jda.api.entities.Member;

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
     * Constructor
     * @param dao db object to write to
     */
    public PermissionHandler(PermissionDAO dao) {
        this.dao = dao;
    }


    /**
     * Checks if a member has permission
     * @return true if has
     */
    public boolean hasPermission(Member member, String permission) {



    }




}
