package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.files.filehandlers.ConfigFileManager;
import de.staticred.dbv2.util.Logger;
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

        DBUtil.getINSTANCE().getLogger().postDebug("Message received by: " + event.getMessage().getContentRaw() + " in " + event.getChannel().getName());

        if (event.getMessage().getContentRaw().isEmpty())
            return;

        if (event.getMember() == null)
            return;

        if (event.getMember().getUser().isBot())
            return;

        if (!DBUtil.getINSTANCE().getConfigFileManager().enabledDiscordMessages()) {
            return;
        }

        ConfigFileManager config = DBUtil.getINSTANCE().getConfigFileManager();

        Logger debug = DBUtil.getINSTANCE().getLogger();

        int deleteTime = config.deleteTime();

        if (config.forceCleanChannel()) {
            debug.postDebug("Clean channel enabled");
            List<String> channelIDs = config.getChanelIDs();

            String channelID = event.getChannel().getId();

            if (channelIDs.isEmpty()) {
                debug.postDebug("CCList is empty");
                debug.postDebug("Executing command handler");
                DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage(), event.getMessage().getContentRaw());
                if (deleteTime > -1)
                    event.getMessage().delete().queueAfter(deleteTime, TimeUnit.SECONDS);
                return;
            }

            if (!channelIDs.contains(channelID)) {
                debug.postDebug("Wrong channel detected, ignoring messages.");
                return;
            }

            if (!config.removeOwnerMessages() && event.getMember().isOwner()) {
                debug.postDebug("Executing command handler");
                DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage(), event.getMessage().getContentRaw());
                return;
            }

            if (!DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage(), event.getMessage().getContentRaw())) {
                debug.postDebug("Invalid command input, deleting message");
                event.getMessage().delete().queue();
                return;
            }

            if (deleteTime > -1)
                event.getMessage().delete().queueAfter(deleteTime, TimeUnit.SECONDS);
        } else {
            debug.postDebug("Executing command handler");
            if (DBUtil.getINSTANCE().getCommandManager().handleDiscordInput(event.getMember(), event.getChannel(), event.getMessage(), event.getMessage().getContentRaw())) {
                if (deleteTime > -1) {
                    event.getMessage().delete().queueAfter(deleteTime, TimeUnit.SECONDS);
                }
            }
        }
    }
}
