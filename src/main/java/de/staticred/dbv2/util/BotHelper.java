package de.staticred.dbv2.util;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

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
    }

    /**
     * registers an event
     * @param event to register
     */
    public static void registerEvent(ListenerAdapter event) {
        jda.addEventListener(event);
    }


}
