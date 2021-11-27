package de.staticred.dbv2.files.util;


import de.staticred.dbv2.DBUtil;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Creates backup for files
 *
 * @author Devin
 * @version 1.0.0
 */
public class FileBackUpHelper {

    /**
     * private constructor - util class
     */
    private FileBackUpHelper() {
        throw new IllegalStateException("Cant init - util class");
    }

    /**
     * Will save a copy a JSONObject into the backups folder
     *
     * @param name of the file
     * @param version of the file
     * @param object content of the file
     */
    public static void createBackUpFor(String name, String version, YamlFile object) throws IOException {
        File backUp = new File(DBUtil.getINSTANCE().getDataFolder() + "/backups/files", version + "-" + name);

        if (backUp.exists())
            return;
        else {
            backUp.getParentFile().mkdirs();
            backUp.createNewFile();
        }

        backUp.getParentFile().mkdirs();
        writeJsonToFile(object.toString(), backUp);
    }


    private static void writeJsonToFile(String json, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file.getAbsolutePath());
        fileWriter.write(json);
        fileWriter.flush();
        fileWriter.close();
    }
}
