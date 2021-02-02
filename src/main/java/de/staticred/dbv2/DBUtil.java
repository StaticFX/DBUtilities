package de.staticred.dbv2;

import de.staticred.dbv2.addon.Addon;
import de.staticred.dbv2.addon.AddonHelper;
import de.staticred.dbv2.commands.discordcommands.InfoDiscordCommand;
import de.staticred.dbv2.commands.mccommands.InfoDBUCommand;
import de.staticred.dbv2.commands.mixcommands.permissionmixcommand.PermissionMixCommand;
import de.staticred.dbv2.commands.util.CommandManager;
import de.staticred.dbv2.discord.events.MessageEvent;
import de.staticred.dbv2.files.FileConstants;
import de.staticred.dbv2.files.util.FileHelper;
import de.staticred.dbv2.info.DataBaseInfo;
import de.staticred.dbv2.networking.db.DataBaseConnector;
import de.staticred.dbv2.permission.PermissionHandler;
import de.staticred.dbv2.util.BotHelper;
import de.staticred.dbv2.util.Logger;
import de.staticred.dbv2.util.Mode;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;

/**
 * DBUtil 2.0
 *
 * DBUtil is an independent bot handling program.
 * Its mainly programmed for bungeecord and bukkit systems, but could theoretically be even more expanded.
 *
 * It provides easy to implement features to easily create addons for this program.
 * Addons can implement all kind of features.
 * Its main usage is to connect a discord server to a minecraft network.
 * This is only the base and contains not a lot of commands.
 * More commands must be implemented via. custom written addons
 *
 * StaticRed the main developer of this program, will provide addons from the beginning and add more over time.
 *
 *
 * 
 * @author StaticRed
 * @version 2.0.0
 */

public class DBUtil {

    /**
     * private instance of the plugin
     * gets set at the moment the plugin starts
     */
    private static DBUtil INSTANCE;

    /**
     * Name of the plugin
     */
    public static final String PLUGIN_NAME = "DBUtilities";


    /**
     * Version of the plugin
     */
    public static final String VERSION = "2.0.0 Beta b5";

    /**
     * time pattern to use globally
     */
    public static final String TIME_PATTERN = "kk:ss dd/MM/yyyy";


    /**
     * FileHelper to manage all the files
     */
    private final FileHelper fileHelper;

    /**
     * connector used to connect to the database
     */
    private final DataBaseConnector dataBaseConnector;


    /*TODO - Add independent command execution (neverthenless bukkit or bungeecord)
           - Add independent events (neverthenless bukkit or bungeecord)
           - Add FileSystem (config.yml, messagefiles think about new system)
           - Add FileSystem updater - Done
           - Add DataBase (with updater from VESE)
           - Add Methods to download database data and store them and also restore them
           - Add AutoBackUp
           - Add LinkingFeatures (MC -> DC, DC -> MC)
           - Add all Old command from DBV 1.0
           - Readd metric system (with new stats aswell)
           - Add updater
           - Add Debugger with javaDocs
           - Find a way to communicate between bukkit and bungeecord without bridging


     */

    /**
     * indicates the mode the plugin is running in
     */
    private final Mode mode;

    /**
     * DatabaseInfo to hold track on the database
     */
    private DataBaseInfo dataBaseInfo;

    /**
     * Logger used all over the plugin
     */
    private final Logger logger;

    /**
     * Location of the executed .jar
     */
    private final File dataFolder;

    /**
     * Directory where there addons are
     */
    private final File addonDirectory;

    /**
     * Nullable
     * Collection containing all loaded addons
     */
    private Collection<Addon> addons;


    /**
     * indicates the command manager
     */
    private final CommandManager commandManager;

    /**
     * PermissionHandler
     * @see PermissionHandler
     */
    private final PermissionHandler permissionHandler;

    /**
     * Main Constructor of DBVerifier 2.0
     * Call this method to start up the plugin.
     * @param mode mode of the plugin
     * @param logger to log on
     */
    public DBUtil(Mode mode, Logger logger) throws IOException {
        this.mode = mode;
        INSTANCE = this;
        this.logger = logger;
        this.fileHelper = new FileHelper();
        this.commandManager = new CommandManager();
        try {
            this.dataFolder = getLocation();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Can't load UTF-8 Decoder for unknown reason");
        }
        this.addonDirectory = AddonHelper.loadAddonDirectory(dataFolder);

        //letting the logger now we are online
        logger.postMessage("Starting " + PLUGIN_NAME + " " + VERSION + " " + mode.toString());
        logger.postMessage("Loading files...");
        logger.postDebug("Found " + PLUGIN_NAME + " in: " + getDataFolder().getAbsolutePath());


        loadFiles();


        this.dataBaseConnector = new DataBaseConnector(FileConstants.CONFIG_FILE_MANAGER.getConfigObject());
        dataBaseConnector.setLogger(logger);
        dataBaseConnector.init();

        this.permissionHandler = new PermissionHandler(false);

        logger.postMessage("Loading Addons");
        addons = AddonHelper.loadAddons(this.addonDirectory);
        logger.postMessage("Successfully loaded " + addons.size() + " addons");


        commandManager.registerDiscordCommand(new InfoDiscordCommand());
        commandManager.registerDCLCommand(new InfoDBUCommand());
        commandManager.registerMixCommand(new PermissionMixCommand());

        try {
            BotHelper.startBot(FileConstants.CONFIG_FILE_MANAGER.getConfigObject().getString("Token"));
        } catch (LoginException e) {
            logger.postError("Cant start bot. Please recheck your token in the config.yml");
            return;
        }

        BotHelper.registerEvent(new MessageEvent());


        this.dataBaseInfo = new DataBaseInfo(false);
    }

    private File getLocation() throws UnsupportedEncodingException {
        URL urlLocation = getClass().getProtectionDomain().getCodeSource().getLocation();
        File jarFileLocation = new File(urlLocation.getPath()).getParentFile();
        String path = URLDecoder.decode(jarFileLocation.getAbsolutePath(), "UTF-8");
        return new File(path + "/" + PLUGIN_NAME);
    }

    private void loadFiles() throws IOException {
        fileHelper.registerManager(FileConstants.CONFIG_FILE_MANAGER);
        fileHelper.registerManager(FileConstants.PERMISSION_FILE_MANAGER);
        fileHelper.load();
    }

    /**
     * @return instance object of the current plugin
     */
    public static DBUtil getINSTANCE() {
        return INSTANCE;
    }

    public DataBaseInfo getDataBaseInfo() {
        return dataBaseInfo;
    }

    public Logger getLogger() {
        return logger;
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public Mode getMode() {
        return mode;
    }

    public Collection<Addon> getAddons() {
        return addons;
    }

    public DataBaseConnector getDataBaseConnector() {
        return dataBaseConnector;
    }

    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }
}
