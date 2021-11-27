package de.staticred.dbv2.commands.util;

import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.DiscordSender;
import de.staticred.dbv2.player.MemberSender;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Models an executable command via. Discord and MC
 *
 * Both Discord and MC will check for any mixed commands, and execute them
 *
 * @author Devin
 * @version 1.0.0
 */
public interface MixCommand {
    /**
     * @return name of the command
     */
    String getName();

    /**
     * only required for the discord part, mc will ignore it
     * @return prefix of the command
     */
    String getPrefix();

    String getPermission();

    /**
     * execute method of every discord command
     * @param sender who send the command
     * @param args of the command
     */
    void executeDC(DiscordSender sender, String[] args);

    void executeMC(DBUPlayer sender, String[] args);


}
