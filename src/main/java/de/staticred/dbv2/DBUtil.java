package de.staticred.dbv2;

import de.staticred.dbv2.addon.Addon;
import de.staticred.dbv2.addon.AddonManager;
import de.staticred.dbv2.commands.discordcommands.HelpDiscordCommand;
import de.staticred.dbv2.commands.discordcommands.InfoDiscordCommand;
import de.staticred.dbv2.commands.mccommands.InfoDBUCommand;
import de.staticred.dbv2.commands.mixcommands.permissionmixcommand.PermissionMixCommand;
import de.staticred.dbv2.commands.util.CommandManager;
import de.staticred.dbv2.constants.DBUtilConstants;
import de.staticred.dbv2.discord.events.BotReadyEvent;
import de.staticred.dbv2.discord.events.MessageEvent;
import de.staticred.dbv2.discord.events.RoleCreateEvent;
import de.staticred.dbv2.discord.events.RoleDeleteEvent;
import de.staticred.dbv2.discord.events.SlashCommandEvent;
import de.staticred.dbv2.events.util.EventManager;
import de.staticred.dbv2.files.filehandlers.CommandFileHandler;
import de.staticred.dbv2.files.filehandlers.ConfigFileManager;
import de.staticred.dbv2.files.filehandlers.MCMessagesFileHandler;
import de.staticred.dbv2.files.util.FileHelper;
import de.staticred.dbv2.info.DataBaseInfo;
import de.staticred.dbv2.networking.db.DataBaseConnector;
import de.staticred.dbv2.permission.PermissionHandler;
import de.staticred.dbv2.permission.filemanager.PermissionFileDAO;
import de.staticred.dbv2.util.BotHelper;
import de.staticred.dbv2.util.Logger;
import de.staticred.dbv2.util.Mode;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
     * Unique ID of the plugin registered at BStats.org
     *
     */
    public static final int PLUGIN_ID = 10253;

    /**
     * Version of the plugin
     */
    public static final String VERSION = "2.0.0 Beta b7";

    /**
     * time pattern to use globally
     */
    public static final String TIME_PATTERN = "HH:mm dd/MM/yyyy";


    /**
     * FileHelper to manage all the files
     */
    private final FileHelper fileHelper;

    /**
     * connector used to connect to the database
     */
    private @Nullable DataBaseConnector dataBaseConnector;


    /*TODO
           - Add independent events (neverthenless bukkit or bungeecord)
           - Add FileSystem (config.yml, messagefiles think about new system)
           - Add FileSystem updater - Done
           - Add DataBase (with updater from VESE)
           - Add LinkingFeatures (MC -> DC, DC -> MC)
           - Add all Old command from DBV 1.0
           - Readd metric system (with new stats aswell)
           - Find a way to communicate between bukkit and bungeecord without bridging / redis

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
     * Nullable
     * Collection containing all loaded addons
     */
    private final Set<Addon> addons;

    /**
     * indicates the command manager
     */
    private final CommandManager commandManager;

    private ConfigFileManager configFileManager;

    private MCMessagesFileHandler mcMessagesFileHandler;

    private CommandFileHandler commandFileHandler;

    private PermissionFileDAO permissionFileDAO;

    /**
     * PermissionHandler
     * @see PermissionHandler
     */
    @Nullable
    private PermissionHandler permissionHandler;

    /**
     * EventManager
     * @see EventManager
     */
    private final EventManager eventManager;

    /**
     * Main Constructor of DBVerifier 2.0
     * Call this method to start up the plugin.
     * @param eventManager eventManager
     * @param mode mode of the plugin
     * @param logger to log on
     */
    public DBUtil(EventManager eventManager, Mode mode, Logger logger) throws IOException {
        long startTime = System.currentTimeMillis();
        this.eventManager = eventManager;
        this.mode = mode;
        INSTANCE = this;
        this.logger = logger;
        this.fileHelper = new FileHelper();
        this.commandManager = new CommandManager();
        addons = new HashSet<>();

        try {
            this.dataFolder = getLocation();
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Can't load UTF-8 Decoder for unknown reason");
        }

        AddonManager addonManager = new AddonManager(this, getDataFolder());

        //letting the logger now we are online
        logger.postMessage("Starting " + PLUGIN_NAME + " " + VERSION + " " + mode.toString());
        logger.postMessage("Loading files...");
        logger.postDebug("Found " + PLUGIN_NAME + " in: " + getDataFolder().getAbsolutePath());


        loadFiles();

        startBot();

        if (!BotHelper.connected)
            return;


        if (configFileManager.useSQL()) {
            loadDB();
            if (!dataBaseInfo.isConnected()) {
                logger.postMessage("§c" + DBUtilConstants.ASCII_ART);
                logger.postMessage("Can't connect to database, not starting plugin.");
            }
        }

        this.permissionHandler = new PermissionHandler(configFileManager.useSQL());

        logger.postMessage("Loading Addons");
        addons.addAll(addonManager.loadAddons());
        logger.postMessage("Finished loaded " + addons.size() + " addons");

        registerCommands();
        eventManager.init();
        registerDiscordEvents();

        commandManager.load();

        logger.postMessageRaw(DBUtilConstants.ASCII_ART);
        long endTime = System.currentTimeMillis() - startTime;
        logger.postMessage("Successfully start in @" + endTime + "ms");
    }

    private void loadDB() {
        boolean connected = true;

        dataBaseConnector = new DataBaseConnector(configFileManager.getConfigObject());
        dataBaseConnector.setLogger(logger);

        try {
            dataBaseConnector.init();
        } catch (Exception e) {
            //Should throw an SQLException if not connected to database properly
            connected = false;
        }

        this.dataBaseInfo = new DataBaseInfo(connected);
    }

    private void startBot() {
        try {
            BotHelper.startBot(configFileManager.getConfigObject().getString("Token"));
        } catch (LoginException e) {
            logger.postMessage("§c" + DBUtilConstants.ASCII_ART);
            logger.postError("Cant start bot. Please recheck your token in the config.yml");
            BotHelper.connected = false;
            return;
        }
        BotHelper.connected = true;
    }

    private void registerDiscordEvents() {
        BotHelper.registerEvent(new MessageEvent());
        BotHelper.registerEvent(new RoleCreateEvent());
        BotHelper.registerEvent(new RoleDeleteEvent());
        BotHelper.registerEvent(new SlashCommandEvent());
        BotHelper.registerEvent(new BotReadyEvent());
    }

    private void registerCommands() {
        commandManager.registerDiscordCommand(new InfoDiscordCommand());
        commandManager.registerDCLCommand(new InfoDBUCommand());
        commandManager.registerMixCommand(new PermissionMixCommand());
        commandManager.registerDiscordCommand(new HelpDiscordCommand());
    }

    private File getLocation() throws UnsupportedEncodingException {
        URL urlLocation = getClass().getProtectionDomain().getCodeSource().getLocation();
        File jarFileLocation = new File(urlLocation.getPath()).getParentFile();
        String path = URLDecoder.decode(jarFileLocation.getAbsolutePath(), "UTF-8");
        return new File(path + "/" + PLUGIN_NAME);
    }

    private void loadFiles() throws IOException {
        configFileManager = new ConfigFileManager(new File(getDataFolder().getAbsoluteFile(), "config.yml"), DBUtil.getINSTANCE().getFileHelper().getFileFromResource("config.yml"));
        fileHelper.registerManager(configFileManager);
        mcMessagesFileHandler = new MCMessagesFileHandler(new File(getDataFolder().getAbsoluteFile() + "/messages", "mc.yml"));
        fileHelper.registerManager(mcMessagesFileHandler);
        commandFileHandler = new CommandFileHandler(new File(getDataFolder().getAbsoluteFile(), "commands.yml"));
        fileHelper.registerManager(commandFileHandler);
        permissionFileDAO = new PermissionFileDAO(new File(getDataFolder().getAbsoluteFile(), "permissions.yml"));
        fileHelper.registerManager(permissionFileDAO);
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

    @Nullable
    public DataBaseConnector getDataBaseConnector() {
        return dataBaseConnector;
    }

    public FileHelper getFileHelper() {
        return fileHelper;
    }

    @Nullable
    public PermissionHandler getPermissionHandler() {
        return permissionHandler;
    }

    public MCMessagesFileHandler getMcMessagesFileHandler() {
        return mcMessagesFileHandler;
    }

    public EventManager getEventManager() {
        return eventManager;
    }
}
