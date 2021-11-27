package de.staticred.dbv2.util;

/**
 * Interface used to log messages
 *
 * @author Devin
 * @version 1.0.0
 */
public interface Logger {

    /**
     * Posts an error the console
     * @param error message
     */
    void postError(String error);

    /**
     * Posts a debug message the console
     * @param debug message
     */
    void postDebug(String debug);

    /**
     * Posts a message the console
     * @param message message
     */
    void postMessage(String message);

    /**
     * Posts a message the console without prefix
     * @param message message
     */
    void postMessageRaw(String message);


}
