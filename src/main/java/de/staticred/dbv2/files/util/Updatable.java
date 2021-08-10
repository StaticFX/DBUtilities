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

    private final String name, location;
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
     * @param location where the file is in resource package
     */
    public Updatable(File current, String location) {
        super(current, location);
        this.name = current.getName();
        this.location = location;
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
    }

    @Override
    public void afterLoad() {
    }

    private boolean isUpdatable() throws IllegalStateException, IllegalArgumentException {
        if (configuration == null)
            throw new IllegalStateException("Method can't be invoked when yml = null");

        YamlFile newestFile = new YamlFile();
        try {
            newestFile.load(getYAMLInputStream(location));
        } catch (IOException | InvalidConfigurationException exception) {
            throw new IllegalStateException("Illegal YAML file");
        }

        int newKeysAmount = newestFile.getKeys(true).size() - configuration.getKeys(true).size();

        DBUtil.getINSTANCE().getLogger().postDebug("Found: " + newKeysAmount + " keys to update to the old file");

        for (String key : newestFile.getKeys(true)) {
            boolean sameAsAtLeastOneKey = false;


            for (String oldLKey: configuration.getKeys(true)) {
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
        YamlFile newYamlFile = new YamlFile();
        try {
            newYamlFile.load(getYAMLInputStream(location));
        } catch (IOException | InvalidConfigurationException exception) {
            throw new IllegalStateException("Illegal YAML file");
        }

        FileBackUpHelper.createBackUpFor(getName(), configuration.getString("VERSION"), configuration);

        configuration.remove("VERSION");


        for (String key : configuration.getKeys(true)) {
            if (!newYamlFile.contains(key)) {
                configuration.set(key, null);
            }
        }


        for (String key : newYamlFile.getKeys(true)) {
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
