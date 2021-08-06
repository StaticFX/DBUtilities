package de.staticred.dbv2.player;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.discord.util.Embed;
import de.staticred.dbv2.util.DoubleOptional;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;

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

    /**
     * @return interactionhook id
     */
    @Override
    public DoubleOptional<Message, InteractionHook> sendMessage(String message) {
        Embed embed = new Embed(message, DBUtil.PLUGIN_NAME, true, Color.ORANGE, sender.getUser().getAvatarUrl());
        return sendEmbed(embed.build());
    }

    @Override
    public boolean hasPermission(String permission) {
        return DBUtil.getINSTANCE().getPermissionHandler().hasPermission(sender.getMember(), permission);
    }

    /**
     * @return interactionhook id
     */
    @Override
    public DoubleOptional<Message, InteractionHook> sendEmbed(MessageEmbed embed) {
        InteractionHook hook = sender.replyEmbeds(embed).complete();
        return new DoubleOptional<>(null, hook.getInteraction().getHook());
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
