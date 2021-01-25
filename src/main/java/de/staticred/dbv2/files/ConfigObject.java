package de.staticred.dbv2.files;


import org.simpleyaml.configuration.file.YamlFile;

/**
 * ConfigObject is an was to only obtain data, but not write data
 *
 * @author Devin
 * @version 1.0.0
 */

public class ConfigObject {

    private final YamlFile data;


    /**
     * constructor to create an ConfigObject
     * @param obj config
     */
    public ConfigObject(YamlFile obj) {
        this.data = obj;
    }

    public Object get(String obj) {
        return data.get(obj);
    }

    public String getString(String obj) {
        return data.getString(obj);
    }

    public int getInt(String obj) {
        return data.getInt(obj);
    }

    public long getLong(String obj) {
        return data.getLong(obj);
    }

    public boolean getBoolean(String obj) {
        return data.getBoolean(obj);
    }

    public double getDouble(String obj) {
        return data.getDouble(obj);
    }
}
