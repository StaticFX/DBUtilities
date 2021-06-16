package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.SlashCommandSender;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;

/**
 * Listens for slash commands on discord
 *
 * @author Devin Fritz
 * @version 1.0.0
 */
public class SlashCommandEvent extends ListenerAdapter {

    @Override
    public void onSlashCommand(net.dv8tion.jda.api.events.interaction.SlashCommandEvent event) {
        // Only accept commands from guilds
        if (event.getGuild() == null)
            return;
        if (event.getMember() == null)
            return;

        String cmd = event.getName();

        if (event.getSubcommandGroup() != null) {
            //no subcommands
            cmd = cmd + event.getSubcommandGroup();
            cmd = cmd + " " + event.getSubcommandName();
        }

        cmd = cmd + " ";

        StringBuilder sb = new StringBuilder(cmd);

        for (OptionMapping data : event.getOptions()) {

            if (data.getType() == OptionType.BOOLEAN) {
                boolean b = data.getAsBoolean();
                sb.append(b).append(" ");
                continue;
            }

            sb.append(data.getAsString()).append(" ");
        }


        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());

        SlashCommandSender sender = new SlashCommandSender(event);

        DBUtil.getINSTANCE().getCommandManager().handleDiscordInputSlashCommand(sb.toString(), sender, event.getTextChannel(), null);

    }
}
