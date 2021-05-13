package de.staticred.dbv2.player;


import java.util.UUID;

/**
 *
 * Interface used for players.
 * This can be used for bukkit/bungeecord players or other systems in the future. So the system only has to be executed via. that later
 *
 * @author Devin
 * @version 1.0.0
 */
public interface DBUPlayer extends CommandSender {

    /**
     * kicks the player
     * @param reason why
     */
    void kick(String reason);

    void sendMessageRaw(String message);

    boolean isConsole();

    UUID getUUID();

    String getName();

}
