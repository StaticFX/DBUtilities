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


    /**
     * Get object.
     *
     * @param obj the obj
     * @return the object
     */
    public Object get(String obj) {
        return data.get(obj);
    }

    /**
     * Gets string.
     *
     * @param obj the obj
     * @return the string
     */
    public String getString(String obj) {
        return data.getString(obj);
    }

    /**
     * Gets int.
     *
     * @param obj the obj
     * @return the int
     */
    public int getInt(String obj) {
        return data.getInt(obj);
    }

    /**
     * Gets long.
     *
     * @param obj the obj
     * @return the long
     */
    public long getLong(String obj) {
        return data.getLong(obj);
    }

    /**
     * Gets boolean.
     *
     * @param obj the obj
     * @return the boolean
     */
    public boolean getBoolean(String obj) {
        return data.getBoolean(obj);
    }

    /**
     * Gets double.
     *
     * @param obj the obj
     * @return the double
     */
    public double getDouble(String obj) {
        return data.getDouble(obj);
    }
}
