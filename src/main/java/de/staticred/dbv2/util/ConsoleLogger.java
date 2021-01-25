package de.staticred.dbv2.util;

public class ConsoleLogger implements Logger {


    @Override
    public void postError(String error) {
        System.out.println("§c§lERROR: §f" + error);
    }

    @Override
    public void postDebug(String debug) {
        System.out.println("§e§lDEBUG: " + debug);
    }

    @Override
    public void postMessage(String message) {
        System.out.println(message);
    }
}
