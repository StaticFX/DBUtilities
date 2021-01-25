package de.staticred.dbv2.player;


/**
 *
 * Interface used for players.
 * This can be used for bukkit/bungeecord players or other systems in the future. So the system only has to be executed via. that later
 *
 * @author Devin
 * @version 1.0.0
 */
public interface DBUPlayer {

    /**
     * sends a message to the player
     * @param message message
     */
    void sendMessage(String message);

    /**
     * kicks the player
     * @param reason why
     */
    void kick(String reason);

    /**
     * Checks if the player has permission
     * @param permission to check
     * @return true if the player has the permission
     */
    boolean hasPermission(String permission);

}
