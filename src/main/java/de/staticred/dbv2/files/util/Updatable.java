package de.staticred.dbv2.files.util;

import de.staticred.dbv2.DBUtil;
import org.jetbrains.annotations.Nullable;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Updatable files will be compared to their newest version and then auto updated.
 *
 * To use this properly extend this class, and the be sure to use the proper params for the constructor.
 * Updating files will work by:
 *
 * comparing keys
 * ->
 * adding new keys
 * deleting old keys
 *
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public abstract class Updatable extends DBUtilFile {

    private final String name;
    @Nullable

    /**
     * Will create a new updatable file
     * it will atomically load the file and update it to the newest file.
     *
     * It will then save the older one as a backup
     * @see FileBackUpHelper
     *
     * To get a file from your resource you can use
     * @see FileHelper
     *
     * @param current file
     * @param newest  file
     * @param location where the file is in resource package
     */
    public Updatable(File current, String location) {
        super(current, location);
        this.name = current.getName();
        update();
    }

    private void update() {
        if (isUpdatable()) {
            try {
                updateFile();
            } catch (IOException exception) {
                DBUtil.getINSTANCE().getLogger().postError("Error while updating " + getName());
                exception.printStackTrace();
            }
        }
        newest.delete();
    }

    @Override
    public void afterLoad() {
    }

    private boolean isUpdatable() throws IllegalStateException, IllegalArgumentException {
        if (configuration == null)
            throw new IllegalStateException("Method can't be invoked when yml = null");

        if (newest.isDirectory())
            throw new IllegalStateException("File can't be a directory");

        if (!newest.canRead())
            throw new IllegalStateException("Can't read file");

        //file is valid
        YamlFile newestFile = new YamlFile(newest);
        try {
            newestFile.load();
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            return false;
        }

        int newKeysAmount = newestFile.getKeys(false).size() - configuration.getKeys(true).size();

        DBUtil.getINSTANCE().getLogger().postDebug("Found: " + newKeysAmount + " keys to update to the old file");

        for (String key : newestFile.getKeys(false)) {
            boolean sameAsAtLeastOneKey = false;

            for (String oldLKey: configuration.getKeys(false)) {
                if (key.equals(oldLKey)) {
                    sameAsAtLeastOneKey = true;
                }
            }

            if (!sameAsAtLeastOneKey)
                return true;
        }

        return false;
    }

    public boolean updateFile() throws IOException {
        YamlFile newYamlFile = new YamlFile(newest);

        try {
            newYamlFile.load();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }

        FileBackUpHelper.createBackUpFor(getName(), configuration.getString("VERSION"), configuration);

        configuration.remove("VERSION");


        for (String key : configuration.getKeys(false)) {
            if (!newYamlFile.contains(key)) {
                configuration.set(key, null);
            }
        }


        for (String key : newYamlFile.getKeys(false)) {
            if (!configuration.contains(key)) {
                configuration.set(key, newYamlFile.get(key));
            }
        }

        configuration.set("VERSION", DBUtil.VERSION);
        saveData();
        return true;
    }


    private InputStream getYAMLInputStream(String resourceLocation) {
        return getClass().getClassLoader().getResourceAsStream(resourceLocation);
    }

    public String getName() {
        return name;
    }

    public @Nullable YamlFile getConfiguration() {
        return configuration;
    }
}
