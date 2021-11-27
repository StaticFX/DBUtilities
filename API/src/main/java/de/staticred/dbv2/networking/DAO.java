package de.staticred.dbv2.networking;


import java.io.IOException;

/**
 * DATA-ACCESS-OBJECT
 *
 * Used to create objects which purpose is it only to access data somewhere
 *
 * @author Devin
 * @version 1.0.0
 */

public interface DAO {


    /**
     * dao starting its own services of whatever it needs
     */
    boolean startDAO() throws IOException;

    /**
     * sava currently unsaved data
     */
    boolean saveData() throws IOException;

}
