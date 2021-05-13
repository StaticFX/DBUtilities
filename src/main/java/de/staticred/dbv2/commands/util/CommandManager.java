package de.staticred.dbv2.commands.util;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.constants.FileConstants;
import de.staticred.dbv2.files.filehandlers.CommandFileHandler;
import de.staticred.dbv2.player.DBUPlayer;
import de.staticred.dbv2.player.MemberSender;
import de.staticred.dbv2.player.SlashCommandSender;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;
import java.util.*;

/**
 * Models a class which handles commands from every instance of the plugin
 *
 *
 * @author Devin
 * @version 1.0.0
 */
public class CommandManager {

    private final ArrayList<DiscordCommand> discordCommands;
    private final ArrayList<DBUCommand> dbuCommands;
    private final ArrayList<MixCommand> mixCommands;
    private CommandFileHandler commandFileHandler;

    /**
     * Constructor.
     */
    public CommandManager() {
        discordCommands = new ArrayList<>();
        dbuCommands = new ArrayList<>();
        mixCommands = new ArrayList<>();
    }

    public void load() {
        commandFileHandler = new CommandFileHandler(new File(DBUtil.getINSTANCE().getDataFolder().getAbsolutePath(), FileConstants.COMMANDS_LOCATION));

        for (DiscordCommand discordCommand : discordCommands) {
            if (!commandFileHandler.hasCommand(discordCommand.getName()))
                commandFileHandler.addCommand(discordCommand);
        }

        for (DBUCommand dbuCommand : dbuCommands) {
            if (!commandFileHandler.hasCommand(dbuCommand.getName()))
                commandFileHandler.addCommand(dbuCommand);
        }

        for (MixCommand mixCommand : mixCommands) {
            if (!commandFileHandler.hasCommand(mixCommand.getName()))
                commandFileHandler.addCommand(mixCommand);
        }



    }

    /**
     * registers a discord command
     * @param command to register
     */
    public void registerDiscordCommand(DiscordCommand command) {

        if (discordCommands.stream().anyMatch(dccommand -> dccommand.getName().equals(command.getName())))
            return;

        discordCommands.add(command);
    }

    /**
     * registers a DBUCommand
     * @param command to register
     */
    public void registerDCLCommand(DBUCommand command) {
        if (dbuCommands.stream().anyMatch(dbucommand -> dbucommand.getName().equalsIgnoreCase(command.getName())))
            return;

        dbuCommands.add(command);
    }


    /**
     * Register mix command.
     *
     * @param command the command
     */
    public void registerMixCommand(MixCommand command) {
        if (mixCommands.stream().anyMatch(mixCommand -> mixCommand.getName().equalsIgnoreCase(command.getName())))
            return;

        mixCommands.add(command);
    }

    /**
     * Handles discord input via. slashcommand
     * @param in string
     * @param sender sender
     * @param tc textchannel
     */
    public void handleDiscordInputSlashCommand(String in, SlashCommandSender sender, TextChannel tc) {
        if (in.isEmpty())
            return;
        String command = getCommand(in);
        String[] args = getArgs(in);

        for (DiscordCommand dcCommand : discordCommands) {
            if (dcCommand.getName().equalsIgnoreCase(command)) {
                dcCommand.execute(sender, tc, args);
                DBUtil.getINSTANCE().getLogger().postMessage("User " + sender.getMember().getEffectiveName() + " executed command: " + command);
                return;
            }
        }


        for (MixCommand mixCommand : mixCommands) {
            if (mixCommand.getName().equalsIgnoreCase(command)) {
                mixCommand.executeDC(sender, args);
                DBUtil.getINSTANCE().getLogger().postMessage("User " + sender.getMember().getEffectiveName() + " executed command: " + command);
                return;

            }

        }
        sender.sendMessage("Command not found");
    }

    /**
     * Handles discord input via. chat
     * @param in string
     * @param member sender
     * @param tc textchannel
     */
    public boolean handleDiscordInput(Member member, TextChannel tc, String in) {
        if (in.isEmpty())
            return true;

        if (member == null)
            return true;

        if (member.getUser().isBot())
            return true;
        System.out.println("here1");


        String command = getCommand(in.substring(1));
        String[] args = getArgs(in.substring(1));

        for (DiscordCommand dcCommand : discordCommands) {
            String commandPrefix = commandFileHandler.getPrefixFor(dcCommand.getName());
            String prefix = in.substring(0, commandPrefix.length());
            List<String> aliases = commandFileHandler.getAliasesFor(dcCommand.getName());
            if ((dcCommand.getName().equalsIgnoreCase(command) || aliases.contains(command)) && commandPrefix.equals(prefix)) {
                dcCommand.execute(new MemberSender(tc, member), tc, args);
                DBUtil.getINSTANCE().getLogger().postMessage("User " + member.getEffectiveName() + " executed command: " + command);
                return true;
            }
        }


        for (MixCommand mixCommand : mixCommands) {
            String commandPrefix = commandFileHandler.getPrefixFor(mixCommand.getName());
            String prefix = in.substring(0, commandPrefix.length());
            List<String> aliases = commandFileHandler.getAliasesFor(mixCommand.getName());
            if ((mixCommand.getName().equalsIgnoreCase(command) || aliases.contains(command) ) && commandPrefix.equals(prefix)) {
                mixCommand.executeDC(new MemberSender(tc, member),args);
                DBUtil.getINSTANCE().getLogger().postMessage("User " + member.getEffectiveName() + " executed command: " + command);
                return true;
            }
        }
        return false;
    }

    /**
     * Handles mc input via. chat
     * @param in string
     * @param player sender
     */
    public void handleMCInput(DBUPlayer player, String in) {
        if (in.isEmpty())
            return;

        String command = getCommand(in);
        String[] args = getArgs(in);

        for (DBUCommand dbuCommand : dbuCommands) {
            List<String> aliases = commandFileHandler.getAliasesFor(dbuCommand.getName());
            if (dbuCommand.getName().equalsIgnoreCase(command) || aliases.contains(command)) {
                dbuCommand.execute(player, args);
                if (!player.isConsole())
                    DBUtil.getINSTANCE().getLogger().postMessage("User " + player.getName() + " executed command :" + command);
                break;
            }
        }

        for (MixCommand mixCommand : mixCommands) {
            List<String> aliases = commandFileHandler.getAliasesFor(mixCommand.getName());
            if (mixCommand.getName().equalsIgnoreCase(command) || aliases.contains(command)) {
                mixCommand.executeMC(player, args);
                if (!player.isConsole())
                    DBUtil.getINSTANCE().getLogger().postMessage("User " + player.getName() + " executed command :" + command);
                break;
            }
        }
    }

    @SuppressWarnings("che`1`ckstyle:ParameterAssignment")
    public boolean doesCommandExist(String in) {
        String cmd = getCommand(in);
        return dbuCommands.stream().anyMatch(dbuCommand -> dbuCommand.getName().equalsIgnoreCase(cmd))
                || mixCommands.stream().anyMatch(dbuCommand -> dbuCommand.getName().equalsIgnoreCase(cmd))
                || discordCommands.stream().anyMatch(discordCommand -> discordCommand.getName().equalsIgnoreCase(cmd));
    }


    /**
     * Gets copy of registered discord commands.
     *
     * @return the copy of registered discord commands
     */
    public List<DiscordCommand> getCopyOfRegisteredDiscordCommands() {
        return new ArrayList<>(discordCommands);
    }

    /**
     * Gets copy of registered mc commands.
     *
     * @return the copy of registered mc commands
     */
    public List<DBUCommand> getCopyOfRegisteredMCCommands() {
        return new ArrayList<>(dbuCommands);

    }

    /**
     * Gets copy of registered mix commands.
     *
     * @return the copy of registered mix commands
     */
    public List<MixCommand> getCopyOfRegisteredMixCommands() {
        return new ArrayList<>(mixCommands);

    }

    private String getCommand(String in) {
        String[] args = in.split(" ");
        return args[0];
    }

    private String[] getArgs(String in) {
        String[] args = in.split(" ");

        if (args.length == 0) {
            return new String[0];
        }

        String[] argsCopy = new String[args.length - 1];
        System.arraycopy(args, 1, argsCopy, 0, args.length - 1);
        return argsCopy;
    }
}
