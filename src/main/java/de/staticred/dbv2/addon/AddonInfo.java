package de.staticred.dbv2.addon;

import java.io.File;

/**
 * Models addoninfo class which will contain information about an addon.
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public class AddonInfo {

    private final String name;
    private final String version;
    private final String author;
    private final String main;
    private final File file;

    public AddonInfo(String name, String version, String author, String main, File file) {
        this.name = name;
        this.version = version;
        this.author = author;
        this.main = main;
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getAuthor() {
        return author;
    }

    public String getMain() {
        return main;
    }
}
