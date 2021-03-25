package de.staticred.dbv2.util;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Command;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandUpdateAction;

import javax.security.auth.login.LoginException;

/**
 * Util class help to manage the bot
 *
 * @author Devin
 * @version 1.0.0
 */
public class BotHelper {

    /**
     * JDA instance for the bot;
     */
    public static JDA jda;

    public static boolean connected;

    /**
     * constructor.
     */
    private BotHelper() {
        throw new IllegalStateException("Can't init - util class");
    }


    /**
     * starts the bot
     * @param token of the bot
     * @throws LoginException if token is wrong
     */
    public static void startBot(String token) throws LoginException {
        jda = JDABuilder.create(token, GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_BANS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_PRESENCES).build();
        if (jda.getGuilds().size() > 1)
            throw new IllegalStateException("The bot can't be on more than one guild at once");

        registerDefaultCommands();
    }

    /**
     * registers an event
     * @param event to register
     */
    public static void registerEvent(ListenerAdapter event) {
        jda.addEventListener(event);
    }

    public static void registerDefaultCommands() {
        CommandUpdateAction commands = jda.updateCommands();

        commands.addCommands(
                new CommandUpdateAction.CommandData("dbset", "Changes enabled state of permission node")
                        .addOption(new CommandUpdateAction.OptionData(Command.OptionType.STRING, "permission", "permission to set the state from")
                                .setRequired(true)) // This command requires a parameter
                        .addOption(new CommandUpdateAction.OptionData(Command.OptionType.BOOLEAN, "state", "set to true or false")
                            .setRequired(true))
        ).queue();

        commands.queue();
    }

}
