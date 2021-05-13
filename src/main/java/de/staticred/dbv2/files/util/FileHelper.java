package de.staticred.dbv2.files.util;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.constants.FileConstants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;

/**
 * class to manage files
 *
 * @author Devin
 * @version 1.0.0
 */
public class FileHelper {

    /**
     * Collection containing all managers
     */
    private Collection<DBUtilFile> managers;

    /**
     * Constructor
     */
    public FileHelper() {
        managers = new ArrayList<>();
    }

    /**
     * Removes an already registered manager
     * @param manager to remove
     * @return true if successfully
     */
    public boolean unregisterManager(DBUtilFile manager) {
        return managers.remove(manager);
    }

    /**
     * registers a manager
     * @param manager to be registered
     */
    public void registerManager(DBUtilFile manager) {
        managers.add(manager);
    }

    /**
     * loads all the managers and also updates them
     */
    public void load() {
        for (DBUtilFile manager : managers) {
            manager.load();
        }

        File file = new File(DBUtil.getINSTANCE().getDataFolder().getAbsolutePath(), "temp");
        file.delete();
    }

    /**
     * gets a file from the internal Plugin resources
     * @param name of the file
     * @return file
     */
    public File getFileFromResource(String name) {
        File file = new File(DBUtil.getINSTANCE().getDataFolder().getAbsolutePath() + "/temp", name);
        file.getParentFile().mkdirs();
        try (InputStream in = DBUtilFile.class.getClassLoader().getResourceAsStream("files/" + name)) {
            if (in == null)
                throw new IOException("Can't load " + name + " from default resource package");

            Files.copy(in, file.toPath());
        } catch (IOException exception) {
            DBUtil.getINSTANCE().getLogger().postError("Cant load file from resource package: ");
            exception.printStackTrace();
        }
        return file;
    }


}
