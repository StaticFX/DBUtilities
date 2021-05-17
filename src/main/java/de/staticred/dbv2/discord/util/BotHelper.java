package de.staticred.dbv2.discord.util;


import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.discord.events.BotReadyEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;
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
     * The guild the bot is sitting on
     */
    public static Guild guild;


    /**
     * timestamp when the bot was started the last time
     */
    public static long botstartup;

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
        jda.getPresence().setActivity(DBUtil.getINSTANCE().getConfigFileManager().getActivity());
        botstartup = System.currentTimeMillis();
        registerEvent(new BotReadyEvent());
    }

    /**
     * registers an event
     * @param event to register
     */
    public static void registerEvent(ListenerAdapter event) {
        jda.addEventListener(event);
    }

    /**
     * Returns role behaving on the useDiscordIds rule
     * @param role either id or name of the role
     * @return role if found, else null
     */
    public static Role getRole(String role) {
        if (role.isEmpty())
            return null;

        if (DBUtil.getINSTANCE().getConfigFileManager().useDiscordIDs())
            return jda.getRoleById(role);
        return jda.getRolesByName(role, true).get(0);
    }


    /**
     * Registers a new command
     * @param command to register
     */
    public static void registerNewCommand(CommandData command) {
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

        CommandData dbpermsCMD = new CommandData("db", "manage discordbot permissions");

        SubcommandData add = new SubcommandData("add", "adds permission to a role")
                .addOption(new OptionData(OptionType.ROLE, "role", "role to add the permission to").setRequired(true))
                .addOption(new OptionData(OptionType.STRING, "permission", "permission to add to the role").setRequired(true));
        SubcommandData list = new SubcommandData("list", "lists data about a role")
                .addOption(new OptionData(OptionType.ROLE, "role", "role to list the data").setRequired(true));

        SubcommandData addInherit = new SubcommandData("addinherit", "adds a new inherit group")
                .addOption(new OptionData(OptionType.ROLE, "role", "this role will inherit from the other").setRequired(true))
                .addOption(new OptionData(OptionType.ROLE, "otherRole", "the first role will inherit from this").setRequired(true));
        SubcommandData backUp = new SubcommandData("backup", "creates a backup from the current permissions config");
        SubcommandData removeInherit = new SubcommandData("removeInherit", "will remove a inherit from a group")
                .addOption(new OptionData(OptionType.ROLE, "role", "role to remove the inherit from").setRequired(true))
                .addOption(new OptionData(OptionType.ROLE, "otherRole", "this role will get removes as inherit").setRequired(true));

        SubcommandData remove = new SubcommandData("remove", "removes permission node from a given role")
                .addOption(new OptionData(OptionType.ROLE, "role", "role to remove the permission from").setRequired(true))
                .addOption(new OptionData(OptionType.STRING, "permission", "the permission node to remove").setRequired(true));
        SubcommandData set = new SubcommandData("set", "sets a given permission node in a role to true or false")
                .addOption(new OptionData(OptionType.ROLE, "role", "role to edit the permission node in").setRequired(true))
                .addOption(new OptionData(OptionType.STRING, "permission", "permission node to edit the state on").setRequired(true))
                .addOption(new OptionData(OptionType.BOOLEAN, "state", "state of the permission node").setRequired(true));

        SubcommandGroupData subCommandGroups = new SubcommandGroupData("perms", "subcommand");
        subCommandGroups.addSubcommand(add).addSubcommand(list).addSubcommand(addInherit)
        .addSubcommand(backUp).addSubcommand(removeInherit).addSubcommand(remove).addSubcommand(set);

        CommandData infoCMD = new CommandData("info", "Displays info about the current dbutil version.");
        CommandData helpCMD = new CommandData("help", "Returns all commands registered.");

        dbpermsCMD.addOption(subCommandGroups);

        jda.updateCommands().queue();

        commands.addCommands(dbpermsCMD).queue();
        commands.addCommands(infoCMD).queue();
        commands.addCommands(helpCMD).queue();



        commands.queue();
    }

}
