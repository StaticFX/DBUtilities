package de.staticred.dbv2.commands.util;

import de.staticred.dbv2.player.DBUPlayer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.ArrayList;
import java.util.Arrays;

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


    /**
     * Constructor.
     */
    public CommandManager() {
        discordCommands = new ArrayList<>();
        dbuCommands = new ArrayList<>();
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



    public void handleDiscordInput(Member member, TextChannel tc, String in) {
        String prefix = in.substring(0, 1);
        in = in.substring(1);
        String command = getCommand(in);
        String[] args = getArgs(in);

        for (DiscordCommand dcCommand : discordCommands) {
            if (dcCommand.getName().equalsIgnoreCase(command) && dcCommand.getPrefix().equals(prefix)) {
                dcCommand.execute(member, tc, args);
                break;
            }
        }
    }

    public void handleMCInput(DBUPlayer player, String in) {
        String command = getCommand(in);
        String[] args = getArgs(in);

        for (DBUCommand dbuCommand : dbuCommands) {
            if (dbuCommand.getName().equalsIgnoreCase(command)) {
                dbuCommand.execute(player, args);
                break;
            }
        }
    }

    @SuppressWarnings("checkstyle:ParameterAssignment")
    public boolean doesCommandExist(String in) {
        String cmd = getCommand(in);
        return dbuCommands.stream().anyMatch(dbuCommand -> dbuCommand.getName().equalsIgnoreCase(cmd));
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
