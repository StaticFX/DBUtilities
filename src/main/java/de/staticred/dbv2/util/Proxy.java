package de.staticred.dbv2.util;

import de.staticred.dbv2.player.DBUPlayer;

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

}
