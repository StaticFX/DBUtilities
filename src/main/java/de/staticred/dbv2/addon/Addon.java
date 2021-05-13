package de.staticred.dbv2.addon;


import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.commands.util.CommandManager;
import de.staticred.dbv2.files.util.FileHelper;
import de.staticred.dbv2.util.Logger;
import de.staticred.dbv2.util.Mode;
import de.staticred.dbv2.util.Proxy;
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
     * Addon information
     */
    private final AddonInfo addonInfo;

    /**
     * constructor.
     * @param info addoninfo
     * @param dataFolder datafolder
     * @param logger main folder
     * @param commandManager commandManager
     */
    public Addon(AddonInfo info, File dataFolder, Logger logger, CommandManager commandManager, Mode mode) {
        this.addonInfo = info;
        this.name = info.getName();
        this.dataFolder = dataFolder;
        this.logger = logger;
        this.commandManager = commandManager;
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


    /**
     * Gets data folder.
     *
     * @return the data folder
     */
    public File getDataFolder() {
        return dataFolder;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets logger.
     *
     * @return the logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Gets command manager.
     *
     * @return the command manager
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    public DBUtil getDBUtil() {
        return DBUtil.getINSTANCE();
    }

    public FileHelper getFileHelper() {
        return DBUtil.getINSTANCE().getFileHelper();
    }

    public Proxy getProxy() {
        return DBUtil.getINSTANCE().getProxy();
    }

    /**
     * Gets addon info.
     *
     * @return the addon info
     */
    public AddonInfo getAddonInfo() {
        return addonInfo;
    }
}
