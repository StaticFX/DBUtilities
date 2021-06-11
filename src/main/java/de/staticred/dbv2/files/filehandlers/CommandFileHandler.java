package de.staticred.dbv2.files.filehandlers;

import de.staticred.dbv2.commands.util.DBUCommand;
import de.staticred.dbv2.commands.util.DiscordCommand;
import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.constants.FileConstants;
import de.staticred.dbv2.files.ConfigObject;
import de.staticred.dbv2.files.util.DBUtilFile;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class CommandFileHandler extends DBUtilFile {

    /**
     * The constant NAME.
     */
    public static final String NAME = FileConstants.RESOURCE_LOCATION + "commands.yml";

    private boolean isValidFile;

    public CommandFileHandler(File commands) {
        super(commands, FileConstants.RESOURCE_LOCATION + "commands.yml");
    }

    /**
     * Adds a command to the file system if not exist
     * @param command to add
     */
    public void addCommand(DBUCommand command) {
        addCommand(command.getName(), "N/A", "MC", command.getPermission(), new ArrayList<>());
    }

    /**
     * Adds a command to the file system if not exist
     * @param command to add
     */
    public void addCommand(DiscordCommand command) {
        addCommand(command.getName(), command.getPrefix(), "DC", command.getPermission(), new ArrayList<>());
    }

    /**
     * Adds a command to the file system if not exist
     * @param command to add
     */
    public void addCommand(MixCommand command) {
        addCommand(command.getName(), command.getPrefix(), "MIX", command.getPermission(), new ArrayList<>());
    }

    public void addCommand(String name, String prefix, String type, String permission, List<String> aliases) {
        if (hasCommand(name))
            return;

        configuration.set(name + ".name", name);
        configuration.set(name + ".type", type);
        configuration.set(name + ".prefix", prefix);
        configuration.set(name + ".aliases", aliases);

        saveData();
    }


    /**
     * @param command to get the prefix from
     * @return set prefix of the command
     */
    public String getPrefixFor(String command) {
        return configuration.getString(command + ".prefix");
    }

    /**
     * @param command to get the aliases from
     * @return set aliases from the command
     */
    public List<String> getAliasesFor(String command) {
        return configuration.getStringList(command + ".aliases");
    }

    /**
     * @param command to check
     * @return true if file contains command
     */
    public boolean hasCommand(String command) {
        return configuration.contains(command);
    }

    @Override
    public String getName() {
        return NAME;
    }

    public ConfigObject getConfigObject() {
        return new ConfigObject(configuration);
    }

    @Override
    public void afterLoad() {
    }

    public void set(String key, Object value) {
        configuration.set(key, value);
    }
}
