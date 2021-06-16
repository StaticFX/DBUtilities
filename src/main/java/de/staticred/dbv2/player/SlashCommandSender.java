package de.staticred.dbv2.player;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.discord.util.Embed;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;

import java.awt.Color;

/**
 * Command was send by an discord slash event
 *
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public class SlashCommandSender implements DiscordSender {

    private final SlashCommandEvent sender;

    public SlashCommandSender(SlashCommandEvent sender) {
        this.sender = sender;
    }

    @Override
    public long sendMessage(String message) {
        Embed embed = new Embed(message, DBUtil.PLUGIN_NAME, true, Color.ORANGE, sender.getUser().getAvatarUrl());
        sendEmbed(embed.build());
    }

    @Override
    public boolean hasPermission(String permission) {
        return DBUtil.getINSTANCE().getPermissionHandler().hasPermission(sender.getMember(), permission);
    }

    @Override
    public void sendEmbed(MessageEmbed embed) {
        sender.replyEmbeds(embed).queue();
    }

    @Override
    public Member getMember() {
        return sender.getMember();
    }

    @Override
    public TextChannel getTextChannel() {
        return sender.getTextChannel();
    }
}
