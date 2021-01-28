package de.staticred.dbv2.commands.mixcommands.permissionmixcommand;

import de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands.*;
import de.staticred.dbv2.commands.util.MixCommand;
import de.staticred.dbv2.player.CommandSender;
import de.staticred.dbv2.player.DBUPlayer;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;


/**
 * Models a command to interface with the permission system
 *
 * @author Devin
 * @version 1.0.0
 */
public class PermissionMixCommand implements MixCommand {

    public static final String NAME = "dbperms";

    public static final String PREFIX = "!";

    public static final String PERMISSION = "dbu.cmd.mix.dbperms";


    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getPrefix() {
        return PREFIX;
    }

    @Override
    public String getPermission() {
        return PERMISSION;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (args.length < 1) {
            sender.sendMessage("- DBUtil Permission System -");
            sender.sendMessage("dbperms add <role> <permission> - adds permission");
            sender.sendMessage("dbperms list <role> - displays the permission");
            sender.sendMessage("dbperms remove <role> <permission> - removes the permission");
            sender.sendMessage("dbperms addInherit <role> <role> - adds inherit role");
            sender.sendMessage("dbperms removeInherit <role> <role> - removes inherit role");
            return;
        }

        String subCommand = args[0];

        switch (args[0].toUpperCase()) {

            case "ADD": {
                new AddSubcommand().execute(sender, args);
                return;
            }
            case "LIST": {
                new ListSubCommand().execute(sender, args);
                return;
            }
            case "REMOVE": {
                new RemoveSubCommand().execute(sender, args);
                return;
            }
            case "ADDINHERIT": {
                new AddInheritSubCommand().execute(sender, args);
                return;
            }
            case "REMOVEINHERIT": {
                new RemoveInheritSubCommand().execute(sender, args);
                return;
            }
        }

        sender.sendMessage("- DBUtil Permission System -");
        sender.sendMessage("dbperms add <role> <permission> - adds permission");
        sender.sendMessage("dbperms list <role> - displays the permission");
        sender.sendMessage("dbperms remove <role> <permission> - removes the permission");
        sender.sendMessage("dbperms addInherit <role> <role> - adds inherit role");
        sender.sendMessage("dbperms removeInherit <role> <role> - removes inherit role");

    }
}
