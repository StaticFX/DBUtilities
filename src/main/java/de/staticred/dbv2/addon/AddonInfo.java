package de.staticred.dbv2.addon;

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

    public AddonInfo(String name, String version, String author, String main) {
        this.name = name;
        this.version = version;
        this.author = author;
        this.main = main;
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
