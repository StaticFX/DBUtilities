package de.staticred.dbv2.files.util;

import de.staticred.dbv2.files.FileConstants;
import de.staticred.dbv2.files.filehandlers.FileManager;

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
    private Collection<FileManager> managers;

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
    public boolean unregisterManager(FileManager manager) {
        return managers.remove(manager);
    }

    /**
     * registers a manager
     * @param manager to be registered
     */
    public void registerManager(FileManager manager) {
        managers.add(manager);
    }


    /**
     * loads all the managers and also updates them
     */
    public void load() throws IOException {

        for (FileManager manager : managers) {
            manager.startDAO();
            File newestFile = getFileFromResource(manager.getName());

            if (manager.isUpdatable(newestFile)) {
                manager.updateFile(newestFile);
            }

            newestFile.delete();
        }

        //all files have been loaded and updated can delete now the temp folder
        FileConstants.TEMP_DIRECTORY.delete();
    }

    /**
     * gets a file from the internal Plugin resources
     * @param name of the file
     * @return file
     * @throws IOException if file not found
     */
    private File getFileFromResource(String name) throws IOException {
        File file = new File(FileConstants.TEMP_DIRECTORY, name);
        file.getParentFile().mkdirs();
        try (InputStream in = FileManager.class.getClassLoader().getResourceAsStream("files/" + name)) {
            if (in == null)
                throw new IOException("Can't load config.yml from default resource package");

            Files.copy(in, file.toPath());
        }
        return file;
    }


}
