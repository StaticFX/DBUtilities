package de.staticred.dbv2.discord.events;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.discord.util.BotHelper;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class BotReadyEvent extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        //BotHelper.registerDefaultCommands();

        if (BotHelper.jda.getGuilds().size() > 1)
            throw new IllegalStateException("The bot can't be on more than one guild at once");

        if (BotHelper.jda.getGuilds().size() == 0) {
            DBUtil.getINSTANCE().getLogger().postMessage("");
        }


        if (BotHelper.jda.getGuilds().size() != 0) {
            BotHelper.guild = BotHelper.jda.getGuilds().get(0);



            BotHelper.registerDefaultCommands();
        }

        BotHelper.registerEvent(new MessageEvent());
        BotHelper.registerEvent(new RoleCreateEvent());
        BotHelper.registerEvent(new RoleDeleteEvent());
        BotHelper.registerEvent(new SlashCommandEvent());

    }

}
