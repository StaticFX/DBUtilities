package de.staticred.dbv2.commands.discordcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.commands.util.DiscordCommand;
import de.staticred.dbv2.player.MemberSender;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 * Command returning general info
 *
 * @author Devin
 * @version 1.0.0
 */
public class InfoDiscordCommand implements DiscordCommand {

    private static final String NAME = "Info";
    private static final String PREFIX = "!";
    private static final String PERMISSION = "dc.cmd.info";


    @Override
    public String getPermission() {
        return PERMISSION;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void execute(MemberSender member, TextChannel tc, String[] args) {
        member.sendMessage("Running " + DBUtil.PLUGIN_NAME + " version: " + DBUtil.VERSION + "\nDeveloped by StaticRed");
    }
}
