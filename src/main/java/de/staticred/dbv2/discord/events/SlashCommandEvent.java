package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands.SetSubCommand;
import de.staticred.dbv2.player.MemberSender;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
        event.acknowledge(true).queue();
        switch (event.getName())
        {
            case "dbset": {

                String permission = event.getOption("permission").getAsString();
                boolean state = event.getOption("state").getAsBoolean();

                new SetSubCommand().execute(new MemberSender(event.getTextChannel(), event.getMember()), new String[]{permission, String.valueOf(state)});
            }
            default:
                event.reply("I can't handle that command right now :(").setEphemeral(true).queue();
        }

    }

}
