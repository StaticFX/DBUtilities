package de.staticred.dbv2.util;

import de.staticred.dbv2.DBUtil;

public class ConsoleLogger implements Logger {


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
        System.out.println("[" + DBUtil.PLUGIN_NAME + "] " + message);
    }
}
