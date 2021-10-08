package de.staticred.dbv2.commands.util;

import de.staticred.dbv2.player.DBUPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * Models an executable command via. MC
 *
 * Bukkit commands can be directly catched from chat or console
 *
 * BungeeCord commands can only be catched via. chat
 * To execute them via console you need an special custom command for it
 *
 * @see
 *
 * @author Devin
 * @version 1.0.0
 */
public interface DBUCommand {

    /**
     * unique command name
     * @return name
     */
    String getName();

    /**
     * required permission to execute the command
     * @return permission
     */
    String getPermission();

    /**
     * executes the command
     * @param player who executed
     * @param args arguments
     */
    void execute(@NotNull DBUPlayer player, String[] args);
}
