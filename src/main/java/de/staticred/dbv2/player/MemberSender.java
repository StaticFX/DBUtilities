package de.staticred.dbv2.player;

import de.staticred.dbv2.DBUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class MemberSender implements CommandSender {

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
    public void sendMessage(String message) {
        tc.sendMessage(message).queue();
    }

    @Override
    public boolean hasPermission(String permission) {
        return DBUtil.getINSTANCE().getPermissionHandler().hasPermission(member, permission);
    }

    public Member getMember() {
        return member;
    }

    public TextChannel getTc() {
        return tc;
    }
}
