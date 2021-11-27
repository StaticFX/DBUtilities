package de.staticred.dbv2.player;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.discord.util.Embed;
import de.staticred.dbv2.util.DoubleOptional;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.interactions.InteractionHook;

import java.awt.Color;
import java.util.concurrent.TimeUnit;

public class MemberSender implements DiscordSender {

    /**
     * TextChannel the message will be send to
     */
    private final TextChannel tc;

    /**
     * executor
     */
    private final Member member;

    /**
     * constructor.
     * @param tc textchannel
     * @param member executor
     */
    public MemberSender(TextChannel tc, Member member) {
        this.tc = tc;
        this.member = member;
    }

    @Override
    public DoubleOptional<Message, InteractionHook> sendMessage(String message) {
        Embed embed = new Embed(message, DBUtil.PLUGIN_NAME, true, Color.ORANGE, member.getUser().getAvatarUrl());
        return sendEmbed(embed.build());
    }

    @Override
    public boolean hasPermission(String permission) {
        return DBUtil.getINSTANCE().getPermissionHandler().hasPermission(member, permission);
    }

    /**
     * Sends a embed in the channel
     *
     * @see de.staticred.dbv2.discord.util.Embed
     *
     * @param embed to send
     */
    public DoubleOptional<Message, InteractionHook> sendEmbed(MessageEmbed embed) {
        int deleteTime = DBUtil.getINSTANCE().getConfigFileManager().deleteTime();


        Message msg;

        if (deleteTime < -1)
            msg = tc.sendMessage(embed).complete();
        else if (deleteTime == -1)
            msg = tc.sendMessage(embed).complete();
         else {
            msg = tc.sendMessage(embed).complete();

            msg.delete().queueAfter(deleteTime, TimeUnit.SECONDS);
        }
         return new DoubleOptional<>(msg, null);
    }

    public Member getMember() {
        return member;
    }

    @Override
    public TextChannel getTextChannel() {
        return tc;
    }
}
