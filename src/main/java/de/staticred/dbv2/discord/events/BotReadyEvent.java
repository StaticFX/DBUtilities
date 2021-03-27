package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.util.BotHelper;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class BotReadyEvent extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        BotHelper.registerDefaultCommands();
    }

}
