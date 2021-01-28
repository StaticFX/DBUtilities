package de.staticred.dbv2.permission.filemanager;

import de.staticred.dbv2.files.ConfigObject;
import de.staticred.dbv2.files.FileConstants;
import de.staticred.dbv2.files.filehandlers.FileManager;
import de.staticred.dbv2.networking.DAO;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class PermissionFileDAO implements FileManager {

    public static final String NAME = "permissions.yml";


    /**
     * location of the file
     */
    private final File file;

    /**
     * conf object to work with
     */
    private YamlFile conf;

    /**
     * whether the file is valid or not
     */
    private boolean isValidFile;

    /**
     * Constructor.
     * @param file location
     */
    public PermissionFileDAO(File file) {
        this.file = file;
    }

    @Override
    public boolean isFilePresent() throws IOException {
        if (!file.exists()) {
            //file does not exist

            file.getParentFile().mkdirs();

            //directories exist
            //now create the file
            try (InputStream in = getClass().getClassLoader().getResourceAsStream(FileConstants.RESOURCE_LOCATION + getName())) {
                if (in == null) {
                    isValidFile = false;
                    throw new IOException("Can't read " + getName() + " from resource package");
                }

                Files.copy(in, file.toPath());
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
        return new ConfigObject(conf);
    }

    @Override
    public void set(String key, Object value) {
        conf.set(key, value);
        saveData();
    }

    @Override
    public boolean startDAO() throws IOException {
        if (!isFilePresent()) {
            isValidFile = false;
            throw new IOException("Can't create file at required location");
        }

        conf = new YamlFile(file);
        try {
            conf.load();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            isValidFile = false;
            return false;
        }

        //file does exist
        return isValidFile = true;
    }

    @Override
    public boolean saveData() {
        try {
            conf.save();
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }

        return true;
    }
}
