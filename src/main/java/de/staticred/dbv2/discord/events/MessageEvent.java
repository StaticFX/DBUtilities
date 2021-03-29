package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.DBUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Called when a message is send in the guild
 *
 * @author Devin
 * @version 1.0.0
 */
public class MessageEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (event.getMessage().getContentRaw().isEmpty())
            return;

        if (event.getMember() == null)
            return;

        if (event.getMember().getUser().isBot())
            return;

        DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage().getContentRaw());
    }

}
