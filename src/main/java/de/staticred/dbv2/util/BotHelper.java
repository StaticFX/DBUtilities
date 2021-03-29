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

    /**
     * whether the bot is connected or not
     */
    public static boolean connected;

    /**
     * The guild the bot is sitting one
     */
    public static Guild guild;

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
        guild = jda.getGuilds().get(0);
    }

    /**
     * registers an event
     * @param event to register
     */
    public static void registerEvent(ListenerAdapter event) {
        jda.addEventListener(event);
    }

    /**
     * Registers a new command
     * @param command to register
     */
    public static void registerNewCommand(CommandUpdateAction.CommandData command) {
        Guild mainGuild = jda.getGuilds().get(0);
        CommandUpdateAction commands = mainGuild.updateCommands();
        commands.addCommands(command).queue();
        commands.queue();
    }

    /**
     * Registers the default commands
     */
    public static void registerDefaultCommands() {

        Guild mainGuild = jda.getGuilds().get(0);

        CommandUpdateAction commands = mainGuild.updateCommands();

        CommandUpdateAction.CommandData dbpermsCMD = new CommandUpdateAction.CommandData("db", "manage discordbot permissions");

        CommandUpdateAction.SubcommandData add = new CommandUpdateAction.SubcommandData("add", "adds permission to a role")
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "role", "role to add the permission to").setRequired(true))
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.STRING, "permission", "permission to add to the role").setRequired(true));
        CommandUpdateAction.SubcommandData list = new CommandUpdateAction.SubcommandData("list", "lists data about a role")
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "role", "role to list the data").setRequired(true));

        CommandUpdateAction.SubcommandData addInherit = new CommandUpdateAction.SubcommandData("addinherit", "adds a new inherit group")
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "role", "this role will inherit from the other").setRequired(true))
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "otherRole", "the first role will inherit from this").setRequired(true));
        CommandUpdateAction.SubcommandData backUp = new CommandUpdateAction.SubcommandData("backup", "creates a backup from the current permissions config");
        CommandUpdateAction.SubcommandData removeInherit = new CommandUpdateAction.SubcommandData("removeInherit", "will remove a inherit from a group")
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "role", "role to remove the inherit from").setRequired(true))
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "otherRole", "this role will get removes as inherit").setRequired(true));

        CommandUpdateAction.SubcommandData remove = new CommandUpdateAction.SubcommandData("remove", "removes permission node from a given role")
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "role", "role to remove the permission from").setRequired(true))
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.STRING, "permission", "the permission node to remove").setRequired(true));
        CommandUpdateAction.SubcommandData set = new CommandUpdateAction.SubcommandData("set", "sets a given permission node in a role to true or false")
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.ROLE, "role", "role to edit the permission node in").setRequired(true))
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.STRING, "permission", "permission node to edit the state on").setRequired(true))
                .addOption(new CommandUpdateAction.OptionData(Command.OptionType.BOOLEAN, "state", "state of the permission node").setRequired(true));

        CommandUpdateAction.SubcommandGroupData subCommandGroups = new CommandUpdateAction.SubcommandGroupData("perms", "subcommand");
        subCommandGroups.addSubcommand(add).addSubcommand(list).addSubcommand(addInherit)
        .addSubcommand(backUp).addSubcommand(removeInherit).addSubcommand(remove).addSubcommand(set);

        CommandUpdateAction.CommandData infoCMD = new CommandUpdateAction.CommandData("info", "Displays info about the current dbutil version.");
        CommandUpdateAction.CommandData helpCMD = new CommandUpdateAction.CommandData("help", "Returns all commands registered.");


        dbpermsCMD.addOption(subCommandGroups);

        jda.updateCommands().queue();

        commands.addCommands(dbpermsCMD, infoCMD, helpCMD).queue();

        commands.queue();
    }

}
