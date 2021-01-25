package de.staticred.dbv2.files.filehandlers;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.files.ConfigObject;
import de.staticred.dbv2.files.util.FileBackUpHelper;
import org.simpleyaml.configuration.file.YamlFile;
import org.simpleyaml.exceptions.InvalidConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * This fill will only the file config.yml
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public class ConfigFileManager implements FileManager {

    public static final String NAME = "config.yml";

    /**
     * indicates the config.yml file
     */
    private File config;

    /**
     * DAO working for YAML files
     */
    private YamlFile dao;


    /**
     * indicates if the config.yml file was readable
     */
    private boolean isValidFile;

    public ConfigFileManager(File config) {
        this.config = config;
    }

    @SuppressWarnings("checkstyle:InnerAssignment")
    @Override
    public boolean startDAO() throws IOException {
        if (!isFilePresent()) {
            isValidFile = false;
            throw new IOException("Can't create file at needed location");
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
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("files/config.yml")) {
                if (in == null) {
                    isValidFile = false;
                    throw new IOException("Can't read config.yml from resource package");
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
    public boolean isUpdatable(File updateAble) throws IllegalStateException, IllegalArgumentException {
        if (dao == null)
            throw new IllegalStateException("Method can't be invoked when jsonObject = null");

        if (updateAble.isDirectory())
            throw new IllegalArgumentException("File can't be a directory");

        if (!updateAble.canRead())
            throw new IllegalArgumentException("Can't read file");

        if (!isValidFile)
            throw  new IllegalStateException("config.yml is not valid");

        //file is valid
        YamlFile newestFile = new YamlFile(updateAble);
        try {
            newestFile.load();
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
            return false;
        }

        int newKeysAmount = newestFile.getKeys(false).size() - dao.getKeys(true).size();

        DBUtil.getINSTANCE().getLogger().postDebug("Found: " + newKeysAmount + " keys to update to the old file");

        for (String key : newestFile.getKeys(false)) {
            boolean sameAsAtLeastOneKey = false;

            for (String oldLKey: dao.getKeys(false)) {
                if (key.equals(oldLKey)) {
                    sameAsAtLeastOneKey = true;
                }
            }

            if (!sameAsAtLeastOneKey)
                return true;
        }

        return false;
    }

    @Override
    public boolean updateFile(File updateAble) throws IOException {
        YamlFile newYamlFile = new YamlFile(updateAble);


        try {
            newYamlFile.load();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return false;
        }

        FileBackUpHelper.createBackFor(NAME, dao.getString("VERSION"), dao);

        dao.remove("VERSION");

        System.out.println(newYamlFile.getString("VERSION"));

        for (String key : dao.getKeys(false)) {
            System.out.println(key);
            if (!newYamlFile.contains(key)) {
                dao.set(key, null);
                System.out.println("removed key");
            }
        }

        System.out.println(newYamlFile.getKeys(false).size());

        for (String key : newYamlFile.getKeys(false)) {
            System.out.println(key);
            if (!dao.contains(key)) {
                dao.set(key, newYamlFile.get(key));
                System.out.println("added key");
            }
        }

        dao.set("VERSION", DBUtil.VERSION);
        saveData();
        return true;
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
        saveData();
    }


}
