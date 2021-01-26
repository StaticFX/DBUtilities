package de.staticred.dbv2.addon;


import de.staticred.dbv2.commands.util.CommandManager;
import de.staticred.dbv2.util.Logger;
import de.staticred.dbv2.util.Mode;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;

/**
 * Models the main part of the DBUtil addon system
 *
 * Addons will be mostly used to extend this base of plugin.
 * Addons main class must implement this addon class
 *
 *
 * Every addon must be placed in the corresponding addon folder of the DBUtil plugin
 * This resource will then find the main class, which must implement the onStart method
 * and create an new instance of the class.
 *
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public abstract class Addon {

    /**
     * Name of the addon
     */
    private final String name;

    /**
     * Location of the DataFolder
     */
    private final File dataFolder;

    /**
     * Main logger of the plugin
     */
    private final Logger logger;


    /**
     * CommandManager
     * @see CommandManager
     */
    private final CommandManager commandManager;


    /**
     * Configuration file for addon.yml file
     */
    private final YamlFile addonInfoFile;


    /**
     * constructor.
     * @param name of the addon
     * @param dataFolder datafolder
     * @param logger main folder
     * @param commandManager commandManager
     */
    public Addon(String name, File dataFolder, Logger logger, CommandManager commandManager, YamlFile addonInfoFile, Mode mode) {
        this.name = name;
        this.dataFolder = dataFolder;
        this.logger = logger;
        this.commandManager = commandManager;
        this.addonInfoFile = addonInfoFile;
    }

    /**
     * will be called when every addon is being loaded
     * mostly on the startup of the dbutil plugin
     */
    public abstract void onStart();


    /**
     * will be called when every addon is being unloaded
     * mostly on the end of the dbutil plugin
     */
    public abstract void onEnd();


    public File getDataFolder() {
        return dataFolder;
    }


    public String getName() {
        return name;
    }

    public Logger getLogger() {
        return logger;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public YamlFile getAddonInfoFile() {
        return addonInfoFile;
    }




}
