package de.staticred.dbv2.commands.mixcommands.permissionmixcommand.subcommands;

import de.staticred.dbv2.DBUtil;
import de.staticred.dbv2.player.CommandSender;

import java.io.File;

/**
 * @author Devin Fritz
 * @version 1.0.0
 */
public class BackUpSubCommand {

    private final static String PERMISSION = "db.cmd.mix.dbperms.tofile";


    public BackUpSubCommand() {
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission(PERMISSION)) {
            sender.sendMessage("You don't have permission for this action!");
            return;
        }

        if (args.length != 1) {
            sender.sendMessage("Use: dbperms backup");
            return;
        }

        DBUtil.getINSTANCE().getPermissionHandler().writeToFile();

        sender.sendMessage("**Done**");
    }
}
