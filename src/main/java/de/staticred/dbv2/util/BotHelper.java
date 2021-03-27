package de.staticred.dbv2.util;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Command;
import net.dv8tion.jda.api.entities.Guild;
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

    }

    /**
     * registers an event
     * @param event to register
     */
    public static void registerEvent(ListenerAdapter event) {
        jda.addEventListener(event);
    }

    public static void registerDefaultCommands() {

        Guild mainGuild = jda.getGuilds().get(0);

        CommandUpdateAction commands = mainGuild.updateCommands();

        CommandUpdateAction.CommandData dbpermsCMD = new CommandUpdateAction.CommandData("db", "manage discordbot permissions");

        CommandUpdateAction.SubcommandData add = new CommandUpdateAction.SubcommandData("add", "adds permission to a role")
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "role", "role to add the permission to").setRequired(true))
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.STRING, "permission", "permission to add to the role").setRequired(true));
        CommandUpdateAction.SubcommandData list = new CommandUpdateAction.SubcommandData("list", "lists data about a role")
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "role", "role to list the data").setRequired(true));

        CommandUpdateAction.SubcommandGroupData subCommandGroups = new CommandUpdateAction.SubcommandGroupData("perms", "subcommand");
        subCommandGroups.addSubcommand(add).addSubcommand(list);



        dbpermsCMD.addOption(subCommandGroups);

        commands.addCommands(dbpermsCMD).queue();

        commands.queue();
    }

}
