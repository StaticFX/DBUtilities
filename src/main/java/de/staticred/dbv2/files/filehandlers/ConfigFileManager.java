package de.staticred.dbv2.files.filehandlers;

import de.staticred.dbv2.constants.FileConstants;
import de.staticred.dbv2.files.ConfigObject;
import de.staticred.dbv2.files.util.Updatable;
import net.dv8tion.jda.api.entities.Activity;

import java.io.File;
import java.util.List;

/**
 * This fill will only the file config.yml
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public class ConfigFileManager extends Updatable {

    public ConfigFileManager(File config) {
        super(config, FileConstants.RESOURCE_LOCATION + "config.yml");
    }

    public ConfigObject getConfigObject() {
        return new ConfigObject(configuration);
    }

    public void set(String key, Object value) {
        configuration.set(key, value);
        saveData();
    }

    public boolean writeDatabaseToLog() {
        return configuration.getBoolean(FileConstants.LOG_DB_TO_FILE);
    }

    /**
     * @return config value USQ_SQL
     */
    public boolean useSQL() {
        return configuration.getBoolean(FileConstants.USQ_SQL);
    }

    /**
     * Debug boolean.
     *
     * @return the boolean
     */
    public boolean debug() {
        return configuration.getBoolean(FileConstants.DEBUG);
    }

    public boolean useDiscordIDs() {
        return configuration.getBoolean(FileConstants.USE_DISCORD_IDS);
    }

    public Activity getActivity() {
        String stringType = configuration.getString(FileConstants.ACTIVITY_TYPE);

        switch (stringType.toUpperCase()) {
            case "PLAYING": {
                return Activity.playing(getActivityDescription());
            }
            case "WATCHING": {
                return Activity.watching(getActivityDescription());
            }
            case "COMPETING": {
                return Activity.competing(getActivityDescription());
            }
            default: {
                return Activity.playing("DBUtil by StaticRed");
            }
        }
    }

    public List<String> getChanelIDs() {
        return configuration.getStringList(FileConstants.CHANNELID);
    }

    public boolean forceCleanChannel() {
        return configuration.getBoolean(FileConstants.FORCE_CLEAN_CHANNEL);
    }

    public boolean removeOwnerMessages() {
        return configuration.getBoolean(FileConstants.REMOVE_OWNER_MESSAGES);
    }

    public boolean enabledDiscordMessages() {
        return configuration.getBoolean(FileConstants.ENABLE_DISCORD_PREFIX);
    }

    public String getActivityDescription() {
        return configuration.getString(FileConstants.ACTIVITY_DESCRIPTION);
    }

    public int deleteTime() {
        return configuration.getInt(FileConstants.DELETE_TIME);
    }

    public String getFooter() {
        return configuration.getString(FileConstants.FOOTER);
    }


}
