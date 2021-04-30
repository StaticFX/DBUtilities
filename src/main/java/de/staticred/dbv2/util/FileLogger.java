package de.staticred.dbv2.util;

import de.staticred.dbv2.DBUtil;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class FileLogger implements Logger {

    private final File directory;

    private final String date;

    private @Nullable File latest;

    private final PrintWriter writer;


    public FileLogger(File directory) throws IOException {
        this.directory = directory;

        if (!directory.exists()) {
            directory.mkdir();
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DBUtil.FILE_TIME_PATTERN);
        date = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        if (directory.listFiles().length == 0)
            this.latest = null;
        else {
            for (File file : directory.listFiles()) {
                if (file.getName().equals("latest.log")) {
                    this.latest = file;
                    renameLatestToDate();
                    break;
                }
            }
        }

        if (latest == null) {
            this.latest = new File(directory, "latest.log");
            this.latest.createNewFile();
        }

        writer = new PrintWriter(this.latest);
    }


    private void renameLatestToDate() {
        File to = new File(latest.getParentFile() + "/" + date + ".log");
        latest.renameTo(to);
    }

    public void disable() {
        renameLatestToDate();
        writer.close();
    }

    @Override
    public void postError(String error) {
        postMessage("[ERROR] " + error);
    }

    @Override
    public void postDebug(String debug) {
        postMessage("[DEBUG] " + debug);
    }

    @Override
    public void postMessageRaw(String message) {
        postMessage(message);
    }

    @Override
    public void postMessage(String message) {
        writeToFile("[" + DBUtil.PLUGIN_NAME + "] " + message);
    }

    private void writeToFile(String message) {
        writer.println(message);
        writer.flush();
    }



}
