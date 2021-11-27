package de.staticred.dbv2.util;


/**
 * Whether the plugin is in Bungeecord, Bukkit or Addon (For bungeecord)
 */
public enum  Mode {
    /**
     * Plugin executed via bukkit
     */
    BUKKIT,

    /**
     * Plugin Executed via BungeeCord
     */
    BUNGEECORD,

    /**
     * Plugin executed via. velocity
     */
    VELOCITY;
}
