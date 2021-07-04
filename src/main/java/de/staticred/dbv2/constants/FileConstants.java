package de.staticred.dbv2.constants;

import de.staticred.dbv2.DBUtil;

import java.io.File;

/**
 * Constants class containing all Strings used for the config
 *
 * This is always a reliable source to obtain data from the config via. keys
 *
 * @author Devin
 * @version 1.0.0
 */
public class FileConstants {

    public static final String TOKEN = "Token";

    public static final String USQ_SQL = "UseSQL";
    public static final String SQL_USER = "SQL_USER";
    public static final String SQL_PORT = "SQL_PORT";
    public static final String SQL_PASSWORD = "SQL_PASSWORD";
    public static final String SQL_DATABASE = "SQL_DATABASE";
    public static final String SQL_USE_SSL = "SQL_USE_SSL";
    public static final String SQL_HOST = "SQL_HOST";
    public static final String LOG_DB_TO_FILE = "logDBToFile";
    public static final String DEBUG = "debugMode";
    public static final String USE_DISCORD_IDS = "useDiscordIDsForRoles";
    public static final String DELETE_TIME = "autoDeleteDiscordMessages";
    public static final String ACTIVITY_TYPE = "botActivityType";
    public static final String ACTIVITY_DESCRIPTION = "botActitivyDescription";
    public static final String CHANNELID = "listenToChannels";
    public static final String REMOVE_OWNER_MESSAGES = "removeOwnerMessages";
    public static final String FORCE_CLEAN_CHANNEL = "forceCleanChannel";

    public static final String PREFIX = "prefix";
    public static final String INVALID_CHANNEL = "discord.invalidChannel";


    public static final String RESOURCE_LOCATION = "files/";

    public static final String COMMANDS_LOCATION = "commands.yml";

    public static final File TEMP_DIRECTORY = new File(DBUtil.getINSTANCE().getDataFolder().getAbsolutePath() + "/temp");
    public static final String ENABLE_DISCORD_PREFIX = "enablePrefixCommands";
}
