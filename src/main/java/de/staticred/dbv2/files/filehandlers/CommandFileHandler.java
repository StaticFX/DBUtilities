package de.staticred.dbv2.files.filehandlers;

import de.staticred.dbv2.files.ConfigObject;
import de.staticred.dbv2.files.FileConstants;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;
import sun.security.krb5.Confounder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class CommandFileHandler implements FileManager {

    /**
     * The constant NAME.
     */
    public static final String NAME = "commands.yml";

    /**
     * indicates the config.yml file
     */
    private final File config;

    /**
     * DAO working for YAML files
     */
    private YamlFile dao;

    private boolean isValidFile;

    public CommandFileHandler(File config) {
        this.config = config;
    }

    @SuppressWarnings("checkstyle:InnerAssignment")
    @Override
    public boolean startDAO() throws IOException {
        if (!isFilePresent()) {
            isValidFile = false;
            throw new IOException("Can't create file at required location");
        }

        dao = new YamlFile(config);
        try {
            dao.load();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }

        //file does exist
        return isValidFile = true;
    }

    @Override
    public boolean saveData() {
        try {
            dao.save();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean isFilePresent() {
        if (!config.exists()) {
            //file does not exist

            config.getParentFile().mkdirs();

            //directories exist
            //now create the file
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(FileConstants.RESOURCE_LOCATION + getName())) {
                if (in == null) {
                    isValidFile = false;
                    throw new IOException("Can't read " + getName() + " from resource package");
                }

                Files.copy(in, config.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

        }
        return isValidFile = true;
    }

    @Override
    public boolean isValidFile() {
        return isValidFile;
    }

    @Override
    public boolean isUpdatable(File updateAble) throws IllegalStateException, IllegalArgumentException, IOException {
        return false;
    }

    @Override
    public boolean updateFile(File newFile) throws IOException {
        return false;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public ConfigObject getConfigObject() {
        return new ConfigObject(dao);
    }



    @Override
    public void set(String key, Object value) {
        dao.set(key, value);
    }
}
