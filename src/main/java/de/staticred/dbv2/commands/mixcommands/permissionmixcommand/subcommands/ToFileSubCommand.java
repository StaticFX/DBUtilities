package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;

import java.io.File;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class ToFileSubCommand {

    private final static String PERMISSION = "db.cmd.mix.dbperms.tofile";


    public ToFileSubCommand() {
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("You don't have permission for this action!");
            return;
        }

        if (args.length != 1) {
            sender.sendMessage("Use: dbperms tofile");
            return;
        }

        DBUtil.getINSTANCE().getPermissionHandler().writeToFile();

        sender.sendMessage("Done");
    }
}
