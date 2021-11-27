package de.staticred.dbv2.util;

import de.staticred.dbv2.DBUtil;

public class ConsoleLogger implements Logger {

    private final String prefix;

    public ConsoleLogger(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void postError(String error) {
        postMessage("[ERROR] " + error);
    }

    @Override
    public void postDebug(String debug) {
        if (DBUtil.getINSTANCE().getConfigFileManager() != null)
            if (DBUtil.getINSTANCE().getConfigFileManager().debug())
                postMessage("[DEBUG] " + debug);
    }

    @Override
    public void postMessageRaw(String message) {
        System.out.println(message);
    }

    @Override
    public void postMessage(String message) {
        System.out.println("[" + prefix + "] " + message);
    }
}
