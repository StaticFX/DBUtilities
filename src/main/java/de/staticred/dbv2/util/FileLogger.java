package de.staticred.dbv2.util;

import de.staticred.dbv2.DBUtil;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DBUtil.TIME_PATTERN);
        date = simpleDateFormat.format(new Date(System.currentTimeMillis()));

        if (directory.listFiles().length == 0)
            this.latest = null;
        else {
            File latest = null;
            for (File file : directory.listFiles()) {
                if (file.getName().equals("latest.log"))
                    latest = file;
            }
            if (latest != null)
                this.latest = latest;
        }

        if (this.latest != null)
            renameLatestToDate();

        if (latest == null) {
            this.latest = new File(directory, "latest.log");
            this.latest.createNewFile();
        }

        writer = new PrintWriter(this.latest);
    }


    private void renameLatestToDate() {
        latest.renameTo(new File(latest.getAbsolutePath(), date + " - generatedLatest.log"));
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
        System.out.println(message);
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
