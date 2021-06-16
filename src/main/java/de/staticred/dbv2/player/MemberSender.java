package de.staticred.dbv2.player;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.discord.util.Embed;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
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
    public long sendMessage(String message) {
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
    public long sendEmbed(MessageEmbed embed) {
        int deleteTime = DBUtil.getINSTANCE().getConfigFileManager().deleteTime();

        long id;

        if (deleteTime < -1)
            id = tc.sendMessage(embed).complete().getIdLong();
        else if (deleteTime == -1)
            id = tc.sendMessage(embed).complete().getIdLong();
         else {
            Message message = tc.sendMessage(embed).complete();
            message.delete().queueAfter(deleteTime, TimeUnit.SECONDS);
            id = message.getIdLong();
        }
         return id;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public TextChannel getTextChannel() {
        return tc;
    }
}
