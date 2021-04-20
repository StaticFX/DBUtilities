package de.staticred.dbv2.files.filehandlers;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.files.ConfigObject;
import de.staticred.dbv2.constants.FileConstants;
import de.staticred.dbv2.files.util.Updatable;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;

/**
 * This fill will only the file config.yml
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public class ConfigFileManager extends Updatable {

    /**
     * indicates if the config.yml file was readable
     */
    private boolean isValidFile;

    public ConfigFileManager(File config, File newest) {
        super(config, newest);
    }

    public ConfigObject getConfigObject() {
        return new ConfigObject(configuration);
    }

    public void set(String key, Object value) {
        configuration.set(key, value);
        saveData();
    }

    /**
     * @return config value USQ_SQL
     */
    public boolean useSQL() {
        return configuration.getBoolean(FileConstants.USQ_SQL);
    }

}
