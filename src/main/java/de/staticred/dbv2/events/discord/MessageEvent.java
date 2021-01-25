package de.staticred.dbv2.events.discord;

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
        DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage().getContentRaw());
    }

}
