package de.staticred.dbv2.constants;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.files.filehandlers.ConfigFileManager;
import de.staticred.dbv2.permission.filemanager.PermissionFileDAO;

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

    public static final String USQ_SQL = "UseSQL";
    public static final String SQL_USER = "SQL_USER";
    public static final String SQL_PORT = "SQL_PORT";
    public static final String SQL_PASSWORD = "SQL_PASSWORD";
    public static final String SQL_DATABASE = "SQL_DATABASE";
    public static final String SQL_USE_SSL = "SQL_USE_SSL";
    public static final String SQL_HOST = "SQL_HOST";

    public static final String PREFIX = "prefix";


    public static final String RESOURCE_LOCATION = "files/";

    public static final String COMMANDS_LOCATION = "commands.yml";

    public static final File TEMP_DIRECTORY = new File(DBUtil.getINSTANCE().getDataFolder().getAbsolutePath() + "/temp");
}
