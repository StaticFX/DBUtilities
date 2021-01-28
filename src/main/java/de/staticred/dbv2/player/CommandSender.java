package de.staticred.dbv2.player;

/**
 * Interface to combine any kind of command sender
 *
 * @author Devin
 * @version 1.0.0
 */
public interface CommandSender {

    /**
     * sends a message to the executor
     * @param message to send
     */
    void sendMessage(String message);

    /**
     * if the executor has the permission
     * @param permission to check on
     */
    boolean hasPermission(String permission);

}
