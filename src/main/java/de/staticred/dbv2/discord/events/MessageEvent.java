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

        DBUtil.getINSTANCE().getLogger().postDebug("Message received by: " + event.getMessage().getContentRaw() + "in " + event.getChannel().getName());

        if (event.getMessage().getContentRaw().isEmpty())
            return;

        if (event.getMember() == null)
            return;

        if (event.getMember().getUser().isBot())
            return;

        if (!event.getMember().isOwner()) {
            if (!DBUtil.getINSTANCE().getConfigFileManager().getChanelIDs().isEmpty()) {
                if (!DBUtil.getINSTANCE().getConfigFileManager().getChanelIDs().contains(event.getChannel().getId())) {
                    if (!DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage().getContentRaw()))
                        event.getMessage().delete().queue();
                    return;
                }
            }
        }

        DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage().getContentRaw());
    }

}
