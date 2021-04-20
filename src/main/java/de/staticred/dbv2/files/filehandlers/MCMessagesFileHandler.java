package de.staticred.dbv2.files.filehandlers;

import de.staticred.dbv2.constants.FileConstants;
import de.staticred.dbv2.files.util.DBUtilFile;

import java.io.File;

/**
 * Manages all the messages in mc.yml
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public class MCMessagesFileHandler extends DBUtilFile {

    /**
     * Instantiates a new Mc messages file handler.
     *
     * @param current the current
     */
    public MCMessagesFileHandler(File current) {
        super(current);
    }

    public String getPrefix() {
        return convertToMcString(configuration.getString(FileConstants.PREFIX));
    }

    private String convertToMcString(String convertable) {
        return convertable.replaceAll("&", "§");
    }

    @Override
    public void afterLoad() {

    }
}
