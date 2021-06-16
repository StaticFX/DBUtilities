package de.staticred.dbv2.player;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Models a sender from a discord
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public interface DiscordSender extends CommandSender {

    /**
     * @return member of the sender
     */
    Member getMember();

    /**
     * @return text channel the message was send to
     */
    TextChannel getTextChannel();


    /**
     * Sends a embed to the sender
     * @param embed the embed
     * @return id of sent message
     */
    long sendEmbed(MessageEmbed embed);

}
