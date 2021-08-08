package de.staticred.dbv2.util;

import de.staticred.dbv2.player.DBUPlayer;

import java.util.UUID;

/**
 * This is a superclass of all kind of proxies
 *
 * A proxy can be an individual spigot server or an bungeecord network
 *
 * @author Devin
 * @version 1.0.0
 */

public interface Proxy {

    /**
     *
     * @param name of the player
     * @return player if found, null otherwise
     */
    DBUPlayer getOnlinePlayer(String name);

    /**
     * Gets player by uuid from proxy
     * @param uuid the uuid
     * @return player if found, null otherwise
     */
    DBUPlayer getPlayer(UUID uuid);


    /**
     * will execute the given command in the console of the proxy
     * @param command to execute
     */
    void executeConsoleCommand(String command);

}
