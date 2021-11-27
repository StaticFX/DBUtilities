package de.staticred.dbv2.player;

import de.staticred.dbv2.util.DoubleOptional;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.InteractionHook;

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
     * @return either messageobject or interactionhook
     */
    DoubleOptional<Message, InteractionHook> sendEmbed(MessageEmbed embed);

}
