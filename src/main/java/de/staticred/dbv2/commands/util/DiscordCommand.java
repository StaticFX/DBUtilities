package de.staticred.dbv2.commands.util;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.Collection;


/**
 * Models an executable command via. Discord
 *
 * DiscordCommandManager will process incomming messages via. discord and reroutes it to the
 * correct command
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public interface DiscordCommand {

    /**
     * @return name of the command
     */
    String getName();

    /**
     * @return prefix of the command
     */
    String getPrefix();

    String getPermission();

    /**
     * execute method of every discord command
     * @param member who executed the command
     * @param tc the command was send in
     * @param args of the command
     */
    void execute(Member member, TextChannel tc, String[] args);


}
