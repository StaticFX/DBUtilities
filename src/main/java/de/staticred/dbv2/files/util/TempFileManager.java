package de.staticred.dbv2.files.util;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Temp file manager will accept any kind of file and create a temp file of it.
 * This file can be deleted anytime, and also will be deleted when the plugin is shutdown.
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public class TempFileManager {

    public static final String TEMP_DIRECTORY = "/temp";

    private final File tempDirectory;
    private final Set<File> tempFiles;

    /**
     * Instantiates a new Temp file manager.
     *
     * @param dataFolder the dataFolder
     */
    public TempFileManager(File dataFolder) {
        this.tempDirectory = new File(dataFolder.getAbsolutePath() + TEMP_DIRECTORY, "temp");
        this.tempFiles = new HashSet<>();

        System.out.println(tempDirectory.getAbsolutePath());
        tempDirectory.mkdirs();
    }


    /**
     * Will create a file with the given name in the temp folder and returns it
     * @param file name of the file to create to
     * @return file which got created
     */
    public File createTempFile(String file) {
        File tempFile = new File(tempDirectory.getAbsolutePath(), file);

        if (tempFile.exists())
            return tempFile;

        try {
            tempFile.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }


        tempFiles.add(tempFile);
        return tempFile;
    }


    public void removeTempFile(String file) {
        for (File tempFile : tempFiles) {
            if (tempFile.getName().equals(file)) {
                tempFiles.remove(tempFile);
                tempFile.delete();
            }
        }
    }

    public void close() {
        for (File tempFile : tempFiles) {
            tempFiles.remove(tempFile);
            tempFile.delete();
        }

        tempDirectory.delete();
    }

}
