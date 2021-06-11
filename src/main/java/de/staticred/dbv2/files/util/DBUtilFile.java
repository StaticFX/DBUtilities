package de.staticred.dbv2.files.util;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.constants.FileConstants;
import org.jetbrains.annotations.Nullable;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * This file represents a user edit file inside of DBUtil
 * The user can edit this files, and you can read or write then to the file.
 * For File language it will use .yml
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public abstract class DBUtilFile {

    private final File file;
    private final String name;
    private @Nullable ClassLoader classLoader;
    protected YamlFile configuration;
    private final String location;

    /**
     * Instantiates a new Db util file.
     *
     * @param current the current, where the file where be saved
     * @param location where to read the file from, searches in resource package for it
     */
    public DBUtilFile(File current, String location) {
        this.classLoader = getClass().getClassLoader();
        this.file = current;
        this.name = current.getName();
        this.location = location;
        load();
    }

    public DBUtilFile(ClassLoader classLoader, File current, String location) {
        this.classLoader = classLoader;
        this.file = current;
        this.name = current.getName();
        this.location = location;
        load();
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }


    /**
     * Loads the current file of this file
     * This will create the file and all its parent directories if none exist
     * As well it will also a load a YamlFile class and load its configuration
     */
    public void load() {
        if (!file.exists()) {
            //file does not exist

            file.getParentFile().mkdirs();

            DBUtil.getINSTANCE().getLogger().postDebug("Loading file: " + location);

            //directories exist
            //now create the file
            try (InputStream in = classLoader.getResourceAsStream(location)) {
                if (in == null) {
                    throw new IOException("Can't read " + location + " from resource package");
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

    /**
     * Saves current configuration to the file
     */
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

    /**
     * Called after loading the file
     */
    public abstract void afterLoad();


    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }


    /**
     * Gets file.
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }


    /**
     * Gets configuration.
     *
     * @return the configuration
     */
    public YamlFile getConfiguration() {
        return configuration;
    }
}
