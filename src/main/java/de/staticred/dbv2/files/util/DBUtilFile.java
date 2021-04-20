package de.staticred.dbv2.files.util;

import de.staticred.dbv2.constants.FileConstants;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * This file represents a user edit file inside of DBUtil
 * The user can edit this files, and you can read or write then to the file.
 * For File language it will use .xml
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public abstract class DBUtilFile {

    private final File file;
    private final String name;
    protected YamlFile configuration;

    public DBUtilFile(File current) {
        this.file = current;
        this.name = file.getName();
        load();
    }

    public void load() {
        if (!file.exists()) {
            //file does not exist

            file.getParentFile().mkdirs();

            //directories exist
            //now create the file
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(FileConstants.RESOURCE_LOCATION + getName())) {
                if (in == null) {
                    throw new IOException("Can't read " + getName() + " from resource package");
                }

                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

        }
        configuration = new YamlFile(file);
        try {
            configuration.load();
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            return;
        }
        afterLoad();
    }

    public void saveData() {
        try {
            configuration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Will reload the configuration of this file.
     * Will not save any unsaved data, so use savaData() before if required
     * Will directly override the current config with the newly read config
     * so be sure to close any operations depending on this configuration before calling it
     */
    public void reloadConfiguration() {
        try {
            configuration.load();
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public abstract void afterLoad();

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }

    public YamlFile getConfiguration() {
        return configuration;
    }
}
