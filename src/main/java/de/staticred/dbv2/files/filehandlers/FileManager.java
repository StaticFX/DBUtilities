package de.staticred.dbv2.files.filehandlers;

import de.staticred.dbv2.files.ConfigObject;
import de.staticred.dbv2.networking.DAO;

import java.io.File;
import java.io.IOException;

/**
 * Interface used for filemanagers
 *
 * @author Devin
 * @version 1.0.0
 */
public interface FileManager extends DAO {


    @Override
    boolean startDAO() throws IOException;

    @Override
    boolean saveData() throws IOException;

    /**
     * if the file is not present, it will create the file with all missing directories
     * @return <code>true</code> if present or was created, <code>false</code> when something went wrong
     * @throws IOException if an error occurred
     */
    boolean isFilePresent() throws IOException;

    /**
     * @return if the file is working correctly
     */
    boolean isValidFile();

    /**
     * if a file is can be updated to another file
     * @param updateAble file to compare to
     * @return <code>true</code> if the old file can be updated to updateAble, <code>false</code> when not
     * @throws IllegalStateException if method is executed before <code>loadFile();</code>
     * @throws IllegalArgumentException if file is a directory, file is unreadable
     * @throws IOException if the new file can't be converted into a JSONObject
     */
    boolean isUpdatable(File updateAble) throws IllegalStateException, IllegalArgumentException, IOException;

    /**
     * updates the old file to the newFile
     * tries to not loose any information in the old file
     *
     * only adds and removes key, it wont change any values or value types, due to easy error in reading/writing
     *
     * use isUpdatable before using this method
     * @see FileManager#isUpdatable(File)
     *
     * @param newFile to update to
     * @throws IOException if the new file can't be converted into a JSONObject
     */
    boolean updateFile(File newFile) throws IOException;


    /**
     * name of the file the manager should manage
     * @return name of the file
     */
    String getName();

    /**
     * @return ConfigObject containing the data in a JSONObject
     */
    ConfigObject getConfigObject();

    /**
     * set a key and value inside of the file
     * @param key key
     * @param value value
     */
    void set(String key, Object value);


}
