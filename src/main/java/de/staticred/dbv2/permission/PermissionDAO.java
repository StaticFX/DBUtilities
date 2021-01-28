package de.staticred.dbv2.permission;

import java.sql.SQLException;

/**
 * Interface to access the daos
 *
 * DAOs can access the file or the
 *
 */
public interface PermissionDAO {

    /**
     * Checks if the id has the permission
     * @param id to check on
     * @param permission to check with
     * @return true if he has the permission
     */
    boolean hasPermission(long id, String permission) throws SQLException;

    /**
     * start the internal services to access the db
     */
    boolean startDAO();


}
