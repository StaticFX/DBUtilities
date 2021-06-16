package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.files.filehandlers.ConfigFileManager;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

        ConfigFileManager config = DBUtil.getINSTANCE().getConfigFileManager();

        int deleteTime = config.deleteTime();

        if (config.forceCleanChannel()) {
            List<String> channelIDs = config.getChanelIDs();

            String channelID = event.getChannel().getId();

            if (channelIDs.isEmpty()) {
                DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage(), event.getMessage().getContentRaw());
                if (deleteTime > -1)
                    event.getMessage().delete().queueAfter(deleteTime, TimeUnit.SECONDS);
                return;
            }

            if (!channelIDs.contains(channelID)) {
                return;
            }

            if (!config.removeOwnerMessages() && event.getMember().isOwner()) {
                DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage(), event.getMessage().getContentRaw());
                return;
            }

            if (!DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage(), event.getMessage().getContentRaw())) {
                event.getMessage().delete().queue();
                return;
            }

            if (deleteTime > -1)
                event.getMessage().delete().queueAfter(deleteTime, TimeUnit.SECONDS);
        } else {
            if (DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage(), event.getMessage().getContentRaw())) {
                if (deleteTime > -1) {
                    event.getMessage().delete().queueAfter(deleteTime, TimeUnit.SECONDS);
                }
            }
        }
    }
}
