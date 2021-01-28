package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.player.CommandSender;

/**
 * Adds permission to a group
 *
 */
public class AddSubcommand {

    private final static String PERMISSION = "db.cmd.mix.dbperms.add";

    /**
     * constructor.
     */
    public AddSubcommand() {
    }

    void execute(CommandSender sender, String[] args) {

        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("You don't have permission for this action!");
            return;
        }

        if (args.length != 3) {
            sender.sendMessage("Use: dbperms add <role> <permission>");
            return;
        }

        String roleString = args[1];

        String permission = args[2];

        




    }



}
