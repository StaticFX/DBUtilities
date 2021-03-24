package de.staticred.dbv2.commands.discordcommands;

import com.sun.corba.se.impl.activation.CommandHandler;
import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.commands.util.CommandManager;
import de.staticred.dbv2.commands.util.DiscordCommand;
import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.discord.util.Embed;
import de.staticred.dbv2.player.MemberSender;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class HelpDiscordCommand implements DiscordCommand {


    private static final String NAME = "Help";
    private static final String PREFIX = "!";
    private static final String PERMISSION = "";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getPermission() {
        return PERMISSION;
    }

    @Override
    public void execute(MemberSender member, TextChannel tc, String[] args) {
        StringBuilder sb = new StringBuilder();
        for (DiscordCommand command : DBUtil.getINSTANCE().getCommandManager().getCopyOfRegisteredDiscordCommands()) {
            sb.append(command.getPrefix()).append(command.getName()).append("\n");
        }

        for (MixCommand command : DBUtil.getINSTANCE().getCommandManager().getCopyOfRegisteredMixCommands()) {
            sb.append(command.getPrefix()).append(command.getName()).append("\n");
        }

        Embed embed = new Embed(sb.toString(), "Discord Commands", true, Color.GREEN);

        member.sendEmbed(embed.build());
    }
}
